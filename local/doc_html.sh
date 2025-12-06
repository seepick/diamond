#!/bin/bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/local}"
cd "${ROOT}" || exit 1

./gradlew :doc:SoftwareDocument:clean :doc:SoftwareDocument:asciidoctor || exit 1
cp -r doc/SoftwareDocument/build/docs/asciidoc/ docs/ || exit 1
open docs/index.html || exit 1
echo "Commit changes for new HTML build."
