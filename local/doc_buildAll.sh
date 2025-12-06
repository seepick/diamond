#!/bin/bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/local}"
cd "${ROOT}" || exit 1
source "./local/_includes.sh"

echoH1 "📚 Building all documentation formats"
echo

./local/doc_diagrams.sh || exit 1
./local/doc_html.sh || exit 1
./local/doc_pdf.sh || exit 1
