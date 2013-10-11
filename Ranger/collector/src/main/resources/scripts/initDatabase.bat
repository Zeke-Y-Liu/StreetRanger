@echo off
set MYSQL_HOME=
if "x%MYSQL_HOME%" == "x" (
	echo Please set MYSQL_HOME
	exit /b 0
)

SET /P ANSWER=Initilize the database will remove all the data in ranger database if exists, do you want to continue (Y/N)? 
echo You chose: %ANSWER% 
if /i {%ANSWER%}=={y} (goto :yes) 
if /i {%ANSWER%}=={yes} (goto :yes) 
goto :no 
:yes 
echo You pressed yes! 
%MYSQL_HOME%\bin\mysql -uroot <initial.sql
exit /b 0 
:no 
echo You pressed no! 
exit /b 1