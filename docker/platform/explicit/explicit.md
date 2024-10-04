Check the architecture information printed in the output

# docker compose

```shell
docker compose up hello-world-arm
docker compose up hello-world-amd
docker compose up hello-world-default
```

# docker run

```shell
docker run --platform=linux/amd64 hello-world
docker run --platform=linux/arm64 hello-world
```
