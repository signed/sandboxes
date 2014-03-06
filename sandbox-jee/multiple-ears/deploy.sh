#!/bin/bash

DEPLOY_DIRECTORY="$HOME/dev/server/jboss-as-7.1.1.Final/standalone/deployments"

rm -f $DEPLOY_DIRECTORY/*.ear

ears=()
ears+=( "one/one-ear/target/one-ear-1.0-SNAPSHOT.ear" )
ears+=( "two/two-ear/target/two-ear-1.0-SNAPSHOT.ear" )

for ear in "${ears[@]}"
do
  cp "$ear" "$DEPLOY_DIRECTORY/"
done

