#!/bin/bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/local}"
cd "${ROOT}" || exit 1
source "./local/_includes.sh"

echoH1 "🤖 Generating OpenAPI sourcecode"
echo ""
echo "Publishing custom generator to Maven local."
./gradlew :shared:openapi-gen:publishToMavenLocal || exit 1
echo "Using custom generator to generate sourcecode."
./gradlew :view:view-model:openApiGenerate || exit 1
echoSuccess "🤖 Generating OpenAPI sourcecode"
