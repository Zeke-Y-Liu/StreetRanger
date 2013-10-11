@echo off
set JAVA_HOME=
if "x%JAVA_HOME%" == "x" (
	echo Please set JAVA_HOME
	exit /b 0
)
cd ..
%JAVA_HOME%\bin\java -Dlog4j.configuration=config/log4j.properties -cp ".;lib/*" com.ranger.collector.CollectorDaemon