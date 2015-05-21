%define install_root /opt/vsolutions
%define init_script vcollectord
%define run_user dbadmin

Name:           vertica-applications
Version:	%{version}
Release:	%{build_number}
Summary:        Vertica data vapplications solution pack

Group:          Utilities
License:        GPL
URL:            http://www.vertica.com
Source:		%{source}
BuildArch:      noarch
BuildRoot:      %{_tmppath}/%{name}-%{version}-%{release}-root-%(%{__id_u} -n)



#BuildRequires: 
#Requires:      

%description
Vertica data vsolutions applications pack


%prep
%setup -q


%build


%install
rm -rf $RPM_BUILD_ROOT
install -d $RPM_BUILD_ROOT%{install_root}
# copy directory tree and files for install
cp -R . $RPM_BUILD_ROOT%{install_root}


%clean
rm -rf $RPM_BUILD_ROOT


%files
#%dir %{install_root}
%defattr(-,root,root,-)
%{install_root}

%pre
rm -rf %{install_root}

%post
#chmod 744 -R %{install_root}
#chmod 754 -R %{install_root}/scripts/start.sh
#echo rpm build root is $RPM_BUILD_ROOT

# copy init script to init.d folder, add script to chkconfig
cp %{install_root}/scripts/%{init_script} /etc/rc.d/init.d/
chkconfig --add %{init_script}

# add user if doesn't already exist, assign as owner of install root
cat /etc/group | grep %{run_user} > /dev/null || groupadd %{run_user}
cat /etc/passwd | grep %{run_user} > /dev/null || useradd -M %{run_user} -p %{run_user}
chown %{run_user}:%{run_user} -R %{install_root}

%preun
# kill the collector framework
%{install_root}/scripts/%{init_script} force_stop

%postun
# remove install root, init script, and pid file
if [ "$1" == "0" ]; then
	chkconfig | grep %{init_script} > /dev/null && chkconfig --del %{init_script}
	echo "Removing %{install_root}"
	rm -rf %{install_root}
	rm -f /etc/rc.d/init.d/%{init_script}
	rm -f /var/run/vcollector.pid
	echo Uninstall completed
else
	echo Upgrade completed
fi
