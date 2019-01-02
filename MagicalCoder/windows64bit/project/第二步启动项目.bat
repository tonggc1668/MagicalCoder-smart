@echo 当前目录 %CD%

set JAVA_HOME=%CD%\..\jdk\jdk1.8.0_45
set M2_HOME=%CD%\apache-maven-3.1.0
set PATH=%JAVA_HOME%\bin;%M2_HOME%\bin;
set CLASSPATH=%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar;%JAVA_HOME%\jre\lib\rt.jar;%JAVA_HOME%\jre\lib\jce.jar;
cd %CD%\youyamvc
 
@echo 正在启动Tomcat ....
cd ..
cd %CD%\apache-tomcat-8.5.4\bin\
startup.bat

pause