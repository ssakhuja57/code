@echo off
color f0
cd ./Help/install
echo.
echo This tool will update your Creo View data by synchronizing your local 
echo directories with those of the FTP site. In order for this tool to 
echo function, two important requirements are:
echo.
echo 1. You are able to establish a connection to the FTP site.
echo 2. Your folder structure is the same as was orginally downloaded from 
echo the FTP Site. (Ignore if this is the initial download)
echo.

winscp /script="./Help/install/winscp_calls.txt"

echo.
echo.
echo Synchronization complete. If you observe that your directories were not
echo properly updated, convey the above results to the FTP administrator. 
echo.
echo Please press any key to close this window.
pause > nul