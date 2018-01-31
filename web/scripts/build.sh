#! /bin/bash

COMMIT=$(git rev-parse HEAD)

(cd ..; yarn; npm run-script build)

mv ../build .

docker build -t quay.io/buildo/cryptonite:web-latest .
docker push quay.io/buildo/cryptonite:web-latest
docker tag quay.io/buildo/cryptonite:web-latest quay.io/buildo/cryptonite:web-$COMMIT
docker push quay.io/buildo/cryptonite:web-$COMMIT

rm -r build || true
