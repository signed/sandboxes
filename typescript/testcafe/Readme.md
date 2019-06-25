# testcafe
## About the project
There is an opensource version and a paid product
[End to End testing with testcafe](https://www.youtube.com/watch?v=i5-EUAIcxLA)

## Facts
- does not use webdriver
- works across different browsers

## Run/Debug in the IDE
[run in webstorm](https://stackoverflow.com/questions/52944207/what-configuration-do-i-need-to-run-testcafe-in-webstorm)
[debug in webstorm](https://devexpress.github.io/tetcafe/documentation/recipes/debug-tests/webstorm.html)

## Problems running in docker
- Increase memory when starting the container https://github.com/DevExpress/testcafe/issues/2946
- https://github.com/DevExpress/testcafe/issues/1883
- https://github.com/DevExpress/testcafe/issues/2461

```bash
docker run --shm-size=1gb
```
```bash
chrome --no-sandbox
```
## Something about roles and local storage
- https://github.com/DevExpress/testcafe/issues/2782
- looks interesting for sharing common setup code between fixtures
