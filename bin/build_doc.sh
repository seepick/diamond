#!/bin/bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/bin}"
cd "${ROOT}" || exit 1
source "./bin/_includes.sh"

echoH1 "📚 Building all documentation formats"

./bin/build_doc_diagrams.sh || exit 1
./bin/build_doc_html.sh || exit 1
./bin/build_doc_pdf.sh || exit 1
echoSuccess "📚 Building all documentation formats"
