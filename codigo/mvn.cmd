@echo off
set "JAVA_HOME=C:\Program Files\Java\jdk-25.0.4"
set "PATH=C:\Program Files\Java\jdk-25.0.4\bin;%PATH%"
set "MAVEN_HOME=C:\Users\juans\.m2\wrapper\dists\apache-maven-3.9.14-bin\1cb7fhup6b5n3bed6kckbrnspv\apache-maven-3.9.14"
"%MAVEN_HOME%\bin\mvn.cmd" %*
