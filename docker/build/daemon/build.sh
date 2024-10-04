#!/usr/bin/env sh

ImageName=daemon

docker build -t ${ImageName} .

# run the entrypoint with the default command
echo docker run -it ${ImageName}
# run the entrypoint and override the command
echo docker run -it ${ImageName} -a
# shell into the container
echo docker run --entrypoint=/bin/sh -it ${ImageName} -c /bin/sh
