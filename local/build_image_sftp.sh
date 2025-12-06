#!/bin/bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/local}"
cd "${ROOT}" || exit 1
source "./local/_includes.sh"

DOCKER_FILE="docker/sftp/Dockerfile"
TARGET_IMAGE="diamond/sftp:latest"

echoH1 "🐳 Building customized SFTP Docker image"
echoParam "📝 Using Docker file" $DOCKER_FILE
echoParam "💾 Registering as image" $TARGET_IMAGE
echo ""
docker build -t $TARGET_IMAGE -f $DOCKER_FILE .
echoSuccess "🐳 Building Docker image"
