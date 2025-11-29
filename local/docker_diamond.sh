#!/bin/bash

./gradlew :app:shadowJar || exit 1
docker build -t diamond/diamond:latest -f docker/diamond/Dockerfile .
