mvn clean release:prepare

git checkout <release-tag>

mvn clean verify

mvn clean release:perform -Psigned-release -Darguments="-DskipTests"
