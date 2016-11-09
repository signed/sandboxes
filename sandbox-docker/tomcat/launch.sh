#!/bin/sh
sed --in-place s/8080/${SERVER_PORT}/ conf/server.xml
catalina.sh run
