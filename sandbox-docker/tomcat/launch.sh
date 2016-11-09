#!/bin/sh
sed --in-place=.bak s/8080/${SERVER_PORT}/ conf/server.xml
catalina.sh run
