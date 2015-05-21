#
# Creating Service Deployment Structure using gradle 
# Author: Tim Donar - June 28, 2014
# Updated By: Mandar Godse - Gradle Updates November 10, 2014
# Updated By: Shubham Sakhuja

#Declare global variables

RPM_BUILD=$HOME/rpmbuild/collections
CHECKOUT=$RPM_BUILD/checkout
PACK_DIR=$(dirname $(dirname $(readlink -f $BASH_SOURCE))) # assuming 'pack' directory is grandparent of this script
REPO_ROOT=$(dirname $PACK_DIR) # assuming git repo root is parent of 'pack' directory
DEPLOY_FOLDER="service-deployment"
STAGE_FOLDER=$RPM_BUILD/stage
RPM_FILE_BASE_NAME="vertica-applications"
SPEC_FILE=vsolutions.spec
VERSION_FILE="$REPO_ROOT/framework/build/resources/main/version.properties"

echo "$(date) - Starting Collector Framework RPM packaging"

function print_param()
{
	echo "$1:   $2"
}

function print_all_params()
{
	echo "Using parameters:"
	print_param "Repository" "$REPO_ROOT"
	print_param "Deployment Folder" "$DEPLOY_FOLDER"
	print_param "Spec File" "$SPEC_FILE"
	echo ""
}

function remove_target_dir()
{
	# Check and clean local target directory
	if [ -d $RPM_BUILD ]; then
	echo "Directory '$(readlink -f $RPM_BUILD)' already exists"
	echo "Are you sure you want to remove the above directory and its contents?"
	echo "Please enter y/n"
	read input
	echo "You entered: $input"
	     if [ $input = 'y' ]; then	
		rm -Rf $RPM_BUILD      	
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
  local gradle_version=$(gradle -v | grep -o -P '(?<=Gradle )[0-9]\.[0-9]*(?<=\.[0-9])')
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

  echo "Starting gradle clean"
  local out=$(gradle clean | grep -o -P '(?<=BUILD).*')	  
  echo "Gradle clean was $out"  
  if [ $out != 'SUCCESSFUL' ]
  then  
  exit 1
  fi

  echo "Starting gradle build"
  out=$(gradle build | grep -o -P '(?<=BUILD).*')	  
  echo "Gradle build was $out"  
  if [ $out != 'SUCCESSFUL' ]
  then  
  exit 1
  fi
  
  echo "Starting gradle pack (generate service deployment structure)"
  out=$(gradle pack | grep -o -P '(?<=BUILD).*')	  
  echo "Gradle pack was $out"  
  if [ $out != 'SUCCESSFUL' ]
  then  
  exit 1
  fi

}

# get version info generated from gradle build
function get_version_info(){
	. $VERSION_FILE
	VERSION=$version
	BUILD_ID=$build_id
	BUILD_NUMBER=$(echo $BUILD_ID | sed -rn "s/^$VERSION-(.*)$/\1/p")

	# fill in values if version info not available
	if [ "$VERSION" == "" ]; then
		echo "WARNING: Version info not found, setting value to UNKNOWN"
		VERSION="UNKNOWN"
		BUILD_NUMBER="0_DEVBUILD"
		BUILD_ID="$VERSION-$BUILD_NUMBER"
	fi

	TARBALL=$RPM_FILE_BASE_NAME-$VERSION
}

# function Retrieve dev branch from the git repo
function git_checkout()
{

	cd $CHECKOUT
	echo "Starting git clone,fetch and checkout to $CHECKOUT"

	git clone $GIT_USER@$GIT_SERVER:$REPO
	cd $REPO_FOLDER
	git fetch origin
	git checkout $BRANCH_NAME
	echo "Sucessfully completed git clone,fetch and checkout"
	

}

# check of rpmbuild, sources and specs folder exists
function check_rpm_build()
{
	if [ ! -d $RPM_BUILD ]
	then
		echo "Creating Directory '$(readlink -f $RPM_BUILD)'"
		mkdir $RPM_BUILD
	fi

	if [ ! -d $RPM_BUILD/SOURCES ]
	then
		echo "Creating Directory '$(readlink -f $RPM_BUILD/SOURCES)'"
		mkdir $RPM_BUILD/SOURCES
	fi

	if [ ! -d $RPM_BUILD/SPECS ]
	then
		echo "Creating Directory '$(readlink -f $RPM_BUILD/SPECS)'"
		mkdir $RPM_BUILD/SPECS
	fi	
}

#
# prep the rpm file
# cp distribution files to rpmbuild
#
function copy_sources()
{

# create tarball from the deployment folder
mkdir -p $STAGE_FOLDER && cd $STAGE_FOLDER
cp -r $REPO_ROOT/$DEPLOY_FOLDER $TARBALL
tar czf $TARBALL.tar.gz $TARBALL

#copy the tar
cp $TARBALL.tar.gz $RPM_BUILD/SOURCES

#copy the specs from pack source location
cp $PACK_DIR/SPECS/*.* $RPM_BUILD/SPECS

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
  rpmbuild --define "_topdir $RPM_BUILD" --define "source $TARBALL.tar.gz" --define "version $VERSION" --define "build_number $BUILD_NUMBER" -bb $SPEC_FILE

}

#first do the gradle installation check
gradle_install_check

#then clean the local target directory
remove_target_dir

#show assumed params
print_all_params

#get the latest git source
#git_checkout

# rebuild target i.e. call gradle, clean, build and install to create full service deployment structure
gradle_build

# get version info generated from gradle build
get_version_info

#check if SOURCES and SPECS and archive folder exists
check_rpm_build

#copy  sources from the service deployment staging area ~/rpmbuild folder
copy_sources

#archive old rpm file
#archive

# generate rpm file
generate_rpm


echo "$(date) - Collector Framework RPM packaging completed sucessfully"

