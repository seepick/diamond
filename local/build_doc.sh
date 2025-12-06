#!/bin/bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/local}"
cd "${ROOT}" || exit 1
source "./local/_includes.sh"

echoH1 "📚 Building all documentation formats"
echo

./local/build_doc_diagrams.sh || exit 1
./local/build_doc_html.sh || exit 1
./local/build_doc_pdf.sh || exit 1
echoSuccess "📚 Building all documentation formats"
