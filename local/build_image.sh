#!/bin/bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/local}"
cd "${ROOT}" || exit 1
source "./local/_includes.sh"

TARGET_IMAGE="diamond/diamond:latest"
DOCKER_FILE="docker/diamond/Dockerfile"

echoH1 "🐳 Building Docker image"
echoParam "📝 Docker file" $DOCKER_FILE
echoParam "💾 Target image" $TARGET_IMAGE
echo ""
./gradlew :app:shadowJar || exit 1
docker build -t $TARGET_IMAGE -f $DOCKER_FILE .
echoSuccess "🐳 Building Docker image"
