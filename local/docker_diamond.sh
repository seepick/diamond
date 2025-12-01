#!/bin/bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/local}"
cd "${ROOT}" || exit 1

./gradlew :app:shadowJar || exit 1
docker build -t diamond/diamond:latest -f docker/diamond/Dockerfile .
