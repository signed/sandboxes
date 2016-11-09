#!/bin/sh
sed --in-place s/8080/${SERVER_PORT}/ /usr/local/tomcat/conf/server.xml
catalina.sh run
