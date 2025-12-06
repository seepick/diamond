#!/bin/bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/local}"
cd "${ROOT}" || exit 1
source "./local/_includes.sh"

TARGET_FILE="docs/Diamond-SoftwareDocument.pdf"

echoH1 "📕 Generating PDF"
echo ""
echo "💾 Target file: $TARGET_FILE"
echo ""
./gradlew :doc:SoftwareDocument:clean :doc:SoftwareDocument:asciidoctorPdf || exit 1
cp doc/SoftwareDocument/build/docs/asciidocPdf/index.pdf $TARGET_FILE || exit 1
echoDocCommitWarning
open $TARGET_FILE || exit 1
echoSuccess "📕 Generating PDF"
