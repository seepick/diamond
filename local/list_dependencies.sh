#!/usr/bin/env bash

TARGET=build/__dependencies__.txt
echo "Generating dependency trees for all submodules and saving it to: [$TARGET]"
./gradlew :doc:SoftwareArchitectureDocument:dependencies > ${TARGET} || exit 1
open ${TARGET}
