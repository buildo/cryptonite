#! /bin/bash

COMMIT=$(git rev-parse HEAD)

(cd ..; sbt assembly)

mv ../target/scala-2.12/cryptonite.jar .

docker build -t quay.io/buildo/cryptonite:latest .
docker push quay.io/buildo/cryptonite:latest
docker tag quay.io/buildo/cryptonite:latest quay.io/buildo/cryptonite:$COMMIT
docker push quay.io/buildo/cryptonite$COMMIT

rm cryptonite.jar || true
