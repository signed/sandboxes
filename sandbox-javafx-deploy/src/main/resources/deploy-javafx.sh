#!/bin/bash

RepositoryURL="http://localhost:8081/nexus/content/repositories/thirdparty/"
RepositoryId="third-party-repository"

mvn deploy:deploy-file \
    -Durl=$RepositoryURL \
    -DrepositoryId=$RepositoryId \
    -DgroupId=com.oracle \
    -DartifactId=javafx \
    -Dversion=2.2.0-beta-09  \
    -Dclassifier=linux-i586 \
    -Dpackaging=jar \
    -Dfile=./javafx/sdk_2_2_0_beta_09/linux-i586/rt/lib/jfxrt.jar


mvn deploy:deploy-file \
    -Durl=$RepositoryURL \
    -DrepositoryId=$RepositoryId \
    -DgroupId=com.oracle \
    -DartifactId=javafx \
    -Dversion=2.2.0-beta-09  \
    -Dclassifier=windows-i586 \
    -Dpackaging=jar \
    -Dfile=./javafx/sdk_2_2_0_beta_09/windows-i586/rt/lib/jfxrt.jar