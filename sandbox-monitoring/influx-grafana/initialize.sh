#!/bin/bash

DOCKER_IP=`docker-machine ip docker-vm`
INFLUX_HOST=http://${DOCKER_IP}:8086
GRAFANA_HOST=http://admin:admin@${DOCKER_IP}:3000

curl -i -XPOST ${INFLUX_HOST}/query --data-urlencode "q=CREATE DATABASE grafana"
curl -i -XPOST ${INFLUX_HOST}/write?db=grafana --data-binary @influx/sample_data.txt


curl ${GRAFANA_HOST}/api/datasources \
     -X POST \
     -H 'Content-Type: application/json;charset=UTF-8' \
     --data-binary @grafana/influx_datasource.json