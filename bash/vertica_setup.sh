#!/bin/bash

## Author: Shubham Sakhuja <shubham.sakhuja@hp.com>



# check if user is root
[ "$(whoami)" != "root" ] && echo "this command must be run as root...exiting" && exit 1


function print_help {

        echo "
This script can be used to automatically install or remove vertica.
Two modes of operation are install and remove.

        install:
                This will download and install vertica on this machine.
                It will:
                - download and install the specified version of the vertica rpm from vdev
                - create specified user with the specified password (unless this user already exists, then password will be unchanged)
                - create and start a db with the specified name and the data and catalog directories in the specified location

        remove:
                This will remove the vertica instance from this local machine by stopping the running database, dropping it, and then uninstalling the rpm (if exactly one is found). 
                It WILL delete all data in the database and it will NOT be recoverable, so be careful when using.
                It will also recursively delete the specified db location folder, so make sure nothing important is in there.

Options:

Name                    Required for Modes      Description

--mode                  -                       mode to run this script (install|remove)
--copy_key		install (initial)	enable passwordless ssh for root to all hosts, REQUIRED to be set to 'true' if first install
--db_name               install                 name of database to create
--hosts              	remove                  comma separated list of hosts to install on, defaults to localhost
--db_user               remove                  vertica admin user, defaults to dbadmin
--db_user_pwd           none	                vertica admin user password, defaults to dbadmin
--db_user_grp		none			vertica admin user group, defaults to verticadba
--db_location           none                  	location on filesystem to house vertica data and catalog directories, defaults to /home/<db_user>/vertica_dbs
--version               none                 	vertica version to download from vdev and install, defaults to latest nightly build

Usage examples:

$script --mode install --db_name myDB
$script --mode install --db_name myDB --version 7.1.0-1 --hosts 192.168.1.10,192.168.1.11,192.168.1.12 
$script --mode remove --db_user dbadmin --db_location /home/dbadmin/vertica_dbs --hosts 192.168.1.10,192.168.1.11,192.168.1.12

"
       exit 1
}



# Default values
db_user="dbadmin"
db_user_pwd="dbadmin"
db_user_grp="verticadba"
#db_location="/home/$db_user/vertica_dbs" ### resolved below arg parsing
hosts="localhost"
copy_key="false"

# required args count
req_install=1
req_remove=2

# counters for required args provided
count_install=0
count_remove=0

# Parse options

while [[ $# > 1 ]]
do
	option="$1"

	case $option in
	--mode)
                mode="$2"
                shift
	;;
        --db_name)
                db_name="$2"
		((count_install++))
                shift
        ;;
        --version)
                version="$2"
                shift
        ;;
	--hosts)
		hosts="$2"
		((count_remove++))
		shift
	;;
        --db_location)
		db_location="$(dirname $2/foo)" # remove trailing slash, if exists
		shift
	;;
	--db_user)
		db_user="$2"
		((count_remove++))
		shift
	;;
	--db_user_pwd)
		db_user_pwd="$2"
		shift
	;;
	--db_user_grp)
		db_user_grp="$2"
		shift
	;;
	--copy_key)
		copy_key="$2"
		shift
	;;
	*)
                echo "unknown option $option"
		print_help
        ;;
        esac
        shift
done

# if db_location not set, set it to 'vertica_dbs' dir in db_user's home dir
[ "$db_location" == "" ] && db_location="/home/$db_user/vertica_dbs"

function copy_root_key {

	for host in $(echo $hosts | tr ',' ' ') # convert commas to spaces
	do
		ssh-copy-id root@$host
	done
}


function on_all_hosts {
	
	cmd="$1"
	
	[ "$hosts" == "localhost" ] && eval "$cmd" && return 0

	for host in $(echo $hosts | tr ',' ' ') # convert commas to spaces
	do
		ssh root@$host "$cmd"
	done
}




# get installed rpms and the count
rpm=$(rpm -qa | grep -E "^vertica-[0-9\.-]+{5,}")
rpm_count=$(rpm -qa | grep -c -E "^vertica-[0-9\.-]{5,}")

# make sure there aren't more than 1 vertica rpms installed, as to not accidentally remove
if [ $rpm_count -gt 1 ]; then
	echo "the below vertica rpms were found installed on machine:"
	echo "$rpm"
	echo "since there are multiple, this script cannot continue..."
	exit 0
fi



if [ "$mode" == "install" ]; then
	
	[ $count_install -ne $req_install ] && echo "missing required args..." && print_help
	
	[ $rpm_count -ne 0 ] && echo "looks like there is already a vertica rpm installed on this machine: $rpm" && echo "exiting..." && exit 0
	
	[ "$copy_key" == "true" ] && copy_root_key

	# build rpm file name
	source="vdev.verticacorp.com/kits/"
	sub_folder="daily/"
	rpm="vertica-x86_64.RHEL5.latest.rpm"

	if [ "$version" != "" ]; then
		sub_folder="releases/"$version"/"
		rpm="vertica-"$version".x86_64.RHEL5.rpm"
	fi

	# get rpm file from vdev
	# 	note: -N option causes not to re-retrieve file unless newer than local
	wget -q -N "http://"$source$sub_folder$rpm --proxy=off

	# run rpm
	rpm -Uvh $rpm

	# run install_vertica
		# for some reason, --accept-eula doesn't always work, so adding heredoc instead
	/opt/vertica/sbin/install_vertica --accept-eula --dba-user $db_user --dba-user-password $db_user_pwd --dba-group $db_user_grp --license CE --failure-threshold=HALT --hosts $hosts --clean -r $rpm

	# make directory to hold dbs
	on_all_hosts "mkdir -p $db_location && chown -R $db_user $db_location"


	# as dbadmin user, use admintools to create db
	su - $db_user -c "admintools -t create_db -s $hosts -d $db_name -D $db_location -c $db_location"

elif [ "$mode" == "remove" ]; then

	[ $count_remove -ne $req_remove ] && echo "missing required args..." && print_help

	# check if admintools installed

	if [ -f "/opt/vertica/bin/admintools" ]; then 

                echo "admintools found installed on this machine"
                
		# get running db
		running_db=$(su - $db_user -c "admintools -t show_active_db")

		# stop running db, if exists
		if [ "$running_db" != "" ]; then
			su - $db_user -c "admintools -t stop_db -d $running_db -F"
		fi
		
		# get all dbs, drop them
		all_dbs=$(echo $(su - $db_user -c "admintools -t db_status -s ALL") | tr ',' ' ')
		for db in $all_dbs
		do		
			su - $db_user -c "admintools -t drop_db -d $db"
		done
	fi
	
	on_all_hosts "rm -f /tmp/4803" # this file is created by vertica and can cause issues if not removed in uninstall

	# remove db_location directory
	on_all_hosts "rm -rf $db_location"

	# kill admintools if running on any machines
	on_all_hosts "killall admintools || true"	

	# find installed vertica rpms, remove if any exist
	on_all_hosts "echo 'removing vertica rpm'; rpm -e vertica- || true"
#	rpm=$(rpm -qa | grep -E "vertica-[0-9\.-]+{5,}")

#	# exit if not exactly one installed vertica rpm
#	if [ $rpm_count -ne 1 ]; then
#		echo "not able to find exactly 1 instance of an installed vertica rpm"
#		echo "the following are installed:"
#		echo $rpm
#		echo "exiting..."
#		exit 2
#	fi

	# remove vertica rpm
#	rpm -e $rpm

else
	echo "invalid mode $mode..."
	print_help
fi
