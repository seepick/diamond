#!/bin/bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/local}"
cd "${ROOT}" || exit 1

./gradlew :shared:openapi-gen:publishToMavenLocal || exit 1
./gradlew :view:view-model:openApiGenerate || exit 1

