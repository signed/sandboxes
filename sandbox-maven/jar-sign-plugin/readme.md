mvn clean verify jarsigner:sign -Djarsigner.alias="cookie" -Djarsigner.keystore="../ci.keystore" -Djarsigner.storepass="pastry" -Djarsigner.keypass="delicious" deploy


mvn clean verify -Psigned-release -DskipTests
