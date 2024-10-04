#!/usr/bin/env sh

ImageName=multi-platform

docker build --platform=linux/amd64,linux/arm64 -t ${ImageName} .

echo docker run -it --platform=linux/amd64 ${ImageName}
echo docker run -it --platform=linux/arm64 ${ImageName}
