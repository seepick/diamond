#!/bin/bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/local}"
cd "${ROOT}" || exit 1

./gradlew :doc:SoftwareDocument:clean :doc:SoftwareDocument:asciidoctorPdf || exit 1
open doc/SoftwareDocument/build/docs/asciidocPdf/index.pdf || exit 1
