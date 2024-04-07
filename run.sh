#!/bin/bash

# export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-21.0.2.0.13-1.fc39.x86_64

mvn clean package

chmod +x target/OcadoRecruitmentTask-1.0-SNAPSHOT.jar

java -jar target/OcadoRecruitmentTask-1.0-SNAPSHOT.jar
