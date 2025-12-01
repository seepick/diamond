#!/usr/bin/env bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/local}"
cd "${ROOT}" || exit 1

TARGET=build/__dependencies__.txt
echo "Generating dependency trees for all submodules and saving it to: [$TARGET]"
./gradlew :doc:SoftwareDocument:dependencies > ${TARGET} || exit 1
open ${TARGET}
