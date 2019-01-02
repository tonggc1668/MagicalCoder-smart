#配置工程环境变量
@echo 当前目录 %CD%
set JAVA_HOME=%CD%\..\jdk\jdk1.8.0_45
set PATH=%JAVA_HOME%\bin
set CLASSPATH=youyajfx-1.0-SNAPSHOT.jar;
java com.magicalcoder.youyajfx.MainApp