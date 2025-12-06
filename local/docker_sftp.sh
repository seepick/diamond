#!/bin/bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/local}"
cd "${ROOT}" || exit 1

docker build -t diamond/sftp:latest -f docker/sftp/Dockerfile .
