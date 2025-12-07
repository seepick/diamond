#!/usr/bin/env bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/bin}"
cd "${ROOT}" || exit 1
source "./bin/_includes.sh"

TARGET_TARGET_FILE=build/__dependencies__.txt

echoH1 "🌳 Generating dependency trees"
echoParam "💾 Target file" $TARGET_TARGET_FILE
echo ""
# :dependencies ... for gradle report
# :dependencyUpdates ... for version plugin
./gradlew :dependencies > ${TARGET_TARGET_FILE} || exit 1
open ${TARGET_TARGET_FILE}
echoSuccess "🌳 Generating dependency trees"
