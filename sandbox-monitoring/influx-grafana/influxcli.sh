#!/usr/bin/env bash

BASE_DIRECTORY=$(pwd)

docker run --rm \
           --net=container:influxgrafana_influx_1 \
           --volume=${BASE_DIRECTORY}/influx:/data \
           -it influxdb:1.1.0-alpine \
           influx -host influxgrafana_influx_1