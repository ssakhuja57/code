#!/bin/bash


# Author: Shubham Sakhuja

#Declare global variables

RPM_BUILD=$HOME/rpmbuild/collections
BRANCH=master
REPO_ROOT=$(dirname $(dirname $(dirname $(readlink -f $BASH_SOURCE)))) # assuming this script is located in $REPO_ROOT/pack/bin/
DEPLOY_FOLDER="service-deployment"
RPM_FILE_BASE_NAME="vertica-applications"
SPEC_FILE=vsolutions.spec
PACKS_HOME="http://git.verticacorp.com/scm/mc/packs.git"
DISTRIBUTED_PACKS=('dc.ap')


function printHelp(){
	
	echo "This script can be used to build the collector rpm. Valid arguments are below."
	echo "	--distPacks		flag that directs to retrieve and distribute with this rpm, packs defined in script"
	echo "	--branch		optional, to build from a specific branch/tag/commit hash (default: $BRANCH)"
	echo "	--noPrompt		flag to run this script without any iteraction"
	echo "	--rpmBuildDir		target location for all rpm related files (default: $RPM_BUILD)"
	echo "	--repoRoot		parent directory for collector source code (default: $REPO_ROOT)"
	echo "	--gradleBin		bin dir where gradle executable is located (defaults to look in PATH)"
	echo "	--version		use to override the version retrieved from git"
	echo "	--release		use to override the build_id retrieved from git"
	echo "	--verbose		verbose logging"
	echo "	--help			print this help text and exit"
	exit 1

}

# parse args

while [[ $# > 0 ]]
do
	option="$1"

	case $option in
	--help)
		printHelp
	;;
	--noPrompt)
		NO_PROMPT="yes"
		shift
	;;
	--rpmBuildDir)
		RPM_BUILD="$(dirname $2/foo)"
		shift
	;;
	--repoRoot)
		REPO_ROOT="$(dirname $2/foo)"
		shift
	;;
	--branch)
		BRANCH="$2"
		shift
	;;
	--distPacks)
		distPacks="yes"
		shift
	;;
	--gradleBin)
		gradleBin="$(dirname $2/foo)"
		shift
	;;
	--version)
		VERSION_OVERRIDE="$2"
		shift
	;;
	--release)
		RELEASE_OVERRIDE="$2"
		shift
	;;
	--verbose)
		verbose="yes"
		shift
	;;
	*)
                echo "unknown option $option"
		printHelp
        ;;
        esac
        shift
done


# set params that depend on args above
VERSION_FILE="$REPO_ROOT/framework/build/resources/main/version.properties"
[ "$gradleBin" != "" ] && GRADLE=$gradleBin/gradle || GRADLE=gradle
STAGE_FOLDER=$RPM_BUILD/stage
CHECKOUT=$RPM_BUILD/checkout



echo "$(date) - Starting Collector Framework RPM packaging"


function print_param()
{
	echo "$1:   $2"
}

function print_all_params()
{
	echo "Using the below parameters..."
	print_param "Repository" "$REPO_ROOT"
	print_param "Deployment Folder" "$DEPLOY_FOLDER"
	print_param "Spec File" "$SPEC_FILE"
	print_param "Branch" "$BRANCH"
	print_param "Version Override" "$VERSION_OVERRIDE"
	print_param "Release Override" "$RELEASE_OVERRIDE"
	echo ""
}

function remove_target_dir()
{
	[ "$NO_PROMPT" == "yes" ] && echo "removing $RPM_BUILD" && rm -rf $RPM_BUILD && return 0
	
	# check and clean target dir
	if [ -d $RPM_BUILD ]; then
		echo "Directory '$(readlink -f $RPM_BUILD)' already exists"
		echo "Are you sure you want to remove the above directory and its contents?"
		echo "Please enter y/n"
		read input
		echo "You entered: $input"
		     if [ $input = 'y' ]; then	
			echo "Beginning collector rpm build process..."
			rm -rf $RPM_BUILD      	
			mkdir -p $RPM_BUILD
		      else
		      echo "Build RPM process will now exit, in order to continue directory must be deleted"
			exit 0
		      fi    
		else
		  mkdir -p $RPM_BUILD
	fi
}

#function checks the installed version of gradle using gradle -v.
function gradle_install_check()
{
  [ "$verbose" == "yes" ] && echo "checking gradle version"
  local gradle_version=$($GRADLE -v | grep -o -P '(?<=Gradle )[0-9]\.[0-9]*(?<=\.[0-9])')
  if [ ! -z $gradle_version ] && [ 1 -eq  `echo "$gradle_version >= 2.1" | bc`  ]
  then
	  echo "Installed Gradle Version is $gradle_version "	
  else
          echo "Install Script Requires Gradle Version 2.1 and greater"
	  echo "Please visit http://www.gradle.org/installation to download and install gradle"
	  exit 1
  fi  
	
}

#function builds the collector framework and creates the service deployment structure
function gradle_build()
{  
  cd $REPO_ROOT

  [ "$verbose" == "yes" ] && echo "Starting gradle clean"
  local out=$($GRADLE clean | grep -o -P '(?<=BUILD).*')	  
  [ "$verbose" == "yes" ] && echo "Gradle clean was $out"  
  if [ $out != 'SUCCESSFUL' ]
  then  
  exit 1
  fi

  [ "$verbose" == "yes" ] && echo "Starting gradle build"
  out=$($GRADLE build | grep -o -P '(?<=BUILD).*')	  
  [ "$verbose" == "yes" ] && echo "Gradle build was $out"  
  if [ $out != 'SUCCESSFUL' ]
  then  
  exit 1
  fi
  
  [ "$verbose" == "yes" ] && echo "Starting gradle pack (generate service deployment structure)"
  out=$($GRADLE pack | grep -o -P '(?<=BUILD).*')	  
  [ "$verbose" == "yes" ] && echo "Gradle pack was $out"  
  if [ $out != 'SUCCESSFUL' ]
  then  
  exit 1
  fi

}

# get version info generated from gradle build
function get_version_info(){	

	. $VERSION_FILE
	VERSION=$version && [ "$verbose" == "yes" ] && echo "setting version number to $VERSION"
	BUILD_ID=$build_id
	BUILD_NUMBER=$(echo $BUILD_ID | sed -rn "s/^$VERSION-(.*)$/\1/p") && [ "$verbose" == "yes" ] && echo "setting build number to $BUILD_NUMBER"

	[ "$VERSION_OVERRIDE" != "" ] && VERSION="$VERSION_OVERRIDE" && [ "$verbose" == "yes" ] && echo "overriding version number with $VERSION_OVERRIDE"
	[ "$RELEASE_OVERRIDE" != "" ] && BUILD_NUMBER="$RELEASE_OVERRIDE" && [ "$verbose" == "yes" ] && echo "overriding release number with $RELEASE_OVERRIDE"

	# fill in values if version info not available
	if [ "$VERSION" == "" ]; then
		[ "$verbose" == "yes" ] && echo "WARNING: Version info not found, setting value to UNKNOWN"
		VERSION="UNKNOWN"
		BUILD_NUMBER="0_DEVBUILD"
	fi

	TARBALL=$RPM_FILE_BASE_NAME-$VERSION
}

function checkout_branch(){
	
	cd $REPO_ROOT
	[ "$verbose" == "yes" ] && echo "checking out $BRANCH"
	git checkout $BRANCH
	
}

# function Retrieve dev branch from the git repo
#function git_clone()
#{
#
#	cd $CHECKOUT
#	echo "Starting git clone,fetch and checkout to $CHECKOUT"
#
#	git clone $GIT_USER@$GIT_SERVER:$REPO
#	cd $REPO_ROOT
#	git fetch origin
#	git checkout $BRANCH_NAME
#	
#	echo "Sucessfully completed git clone,fetch and checkout"
#	
#
#}

# check of rpmbuild, sources and specs folder exists
function check_rpm_build()
{
	if [ ! -d $RPM_BUILD ]
	then
		[ "$verbose" == "yes" ] && echo "Creating Directory '$(readlink -f $RPM_BUILD)'"
		mkdir $RPM_BUILD
	fi

	if [ ! -d $RPM_BUILD/SOURCES ]
	then
		[ "$verbose" == "yes" ] && echo "Creating Directory '$(readlink -f $RPM_BUILD/SOURCES)'"
		mkdir $RPM_BUILD/SOURCES
	fi

	if [ ! -d $RPM_BUILD/SPECS ]
	then
		[ "$verbose" == "yes" ] && echo "Creating Directory '$(readlink -f $RPM_BUILD/SPECS)'"
		mkdir $RPM_BUILD/SPECS
	fi	
}

#
# prep the rpm file
# cp distribution files to rpmbuild
#
function copy_sources(){

	[ "$verbose" == "yes" ] && echo "copy the deployment folder $DEPLOY_FOLDER to staging location $STAGE_FOLDER"
	mkdir -p $STAGE_FOLDER && cd $STAGE_FOLDER
	cp -r $REPO_ROOT/$DEPLOY_FOLDER $TARBALL
}


function create_tar(){
	
	[ "$verbose" == "yes" ] && echo "creating tarball for $TARBALL"
	tar czf $TARBALL.tar.gz $TARBALL
	
	[ "$verbose" == "yes" ] && echo "copying tarball to $RPM_BUILD/SOURCES"
	cp $TARBALL.tar.gz $RPM_BUILD/SOURCES
	
	[ "$verbose" == "yes" ] && echo "copying spec files from $REPO_ROOT/pack/SPECS to $RPM_BUILD/SPECS"
	cp $REPO_ROOT/pack/SPECS/*.* $RPM_BUILD/SPECS

}

#function would archive old files if the file exists under existing rpm build
function archive()
{
    	if [ ! -d $RPM_ARCHIVE ]; then
		echo "Directory '$(readlink -f $RPM_ARCHIVE)' does not exists now creating one"
		mkdir $RPM_ARCHIVE   			             
	fi
	
 	if [ -d $RPM_BUILD/RPMS/noarch ]; then	
		dt=`date +"%m-%d-%Y"`
		fileName=$RPM_FILE_BASE_NAME.noarch.${dt}.rpm
		echo "Archiving the existing file to $RPM_ARCHIVE/$fileName"
		cp $RPM_BUILD/RPMS/noarch/$RPM_FILE_BASE_NAME.*noarch.rpm $RPM_ARCHIVE/$fileName	
	fi

}

#function generates the actual rpm file
function generate_rpm()
{
  local rpmbuild_version=$(rpmbuild --version | grep -o -P '(?<=RPM version )\d\.\d*\d')
  if [ ! -z $rpmbuild_version ] && [ 1 -eq  `echo "$rpmbuild_version <= 4.5" | bc`  ]; then
  		# older versions of rpmbuild require these settings
  		mkdir -p $RPM_BUILD/BUILD
  		mkdir -p $RPM_BUILD/RPMS
  fi  
  cd $RPM_BUILD/SPECS
	[ "$verbose" == "yes" ] && echo "generating rpm"	
  rpmbuild --define "_topdir $RPM_BUILD" --define "source $TARBALL.tar.gz" --define "version $VERSION" --define "build_number $BUILD_NUMBER" -bb $SPEC_FILE

}

# perform any required cleanup
function cleanup(){
	[ "$verbose" == "yes" ] && echo "cleaning up"
	cd $REPO_ROOT
	git checkout master
}

function retrieve_packs(){
	
	[ "$verbose" == "yes" ] && echo "retrieving packs $DISTRIBUTED_PACKS from $PACKS_HOME"
	cd $STAGE_FOLDER
	git clone $PACKS_HOME
	for pack in $DISTRIBUTED_PACKS
	do
		cp -r packs/packages/$pack $TARBALL/packages/ || { echo "unable to find $pack, exiting..." && exit 1; }
	done 
}

# print defined params
print_all_params

#first do the gradle installation check
gradle_install_check

# checkout specified branch
checkout_branch

#then clean the local target directory
remove_target_dir

#get the latest git source
#git_clone

# rebuild target i.e. call gradle, clean, build and install to create full service deployment structure
gradle_build

# get version info generated from gradle build
get_version_info

#check if SOURCES and SPECS and archive folder exists
check_rpm_build

#copy  sources from the service deployment staging area ~/rpmbuild folder
copy_sources

# retrieve packs to be distributed with the collector, if params was passed
[ "$distPacks" == "yes" ] && retrieve_packs

# create tar
create_tar

#archive old rpm file
#archive

# generate rpm file
generate_rpm

# cleanup
cleanup


echo "$(date) - Collector Framework RPM packaging completed sucessfully"



