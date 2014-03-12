set /A cur24_hr=%time:~0,2%
set /A end_hr="%cur24_hr%%%12+1"
if %cur24_hr% EQU 23 (set M=AM)
if %cur24_hr% GEQ 0 (if %cur24_hr% LEQ 10 (set M=AM))
if %cur24_hr% EQU 11 (set M=PM)
if %cur24_hr% GEQ 12 (if %cur24_hr% LEQ 22 (set M=PM))

set std_conf="C:\Program Files (x86)\Apache Software Foundation\Apache2.2\conf\httpd.conf"
set maint_conf="C:\Program Files (x86)\Apache Software Foundation\Apache2.2\conf\httpd_main.conf"
set maint_page="C:\Program Files (x86)\Apache Software Foundation\Apache2.2\htdocs\test\test1.html"

echo The system is currently under nightly maintenance until %end_hr%%time:~2,3% %M% > "%maint_page%"
move "%std_conf%" "%std_conf%_std"
move "%maint_conf%" "%std_conf%"
net stop Apache2.2 && net start Apache2.2
timeout 30
move "%std_conf%" "%maint_conf%"
move "%std_conf%_std" "%std_conf%"
net stop Apache2.2 && net start Apache2.2
