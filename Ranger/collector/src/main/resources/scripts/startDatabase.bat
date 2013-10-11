@echo off
set MYSQL_HOME=
if "x%MYSQL_HOME%" == "x" (
	echo Please set MYSQL_HOME
	exit /b 0
)
start %MYSQL_HOME%\bin\mysqld.exe --console