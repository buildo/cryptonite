#! /bin/bash

COMMIT=$(git rev-parse HEAD)

(cd ..; sbt assembly)

mv ../target/scala-2.12/cryptonite.jar .

docker build -t quay.io/buildo/cryptonite:api-latest .
docker push quay.io/buildo/cryptonite:api-latest
docker tag quay.io/buildo/cryptonite:api-latest quay.io/buildo/cryptonite:api-$COMMIT
docker push quay.io/buildo/cryptonite:api-$COMMIT

rm cryptonite.jar || true
