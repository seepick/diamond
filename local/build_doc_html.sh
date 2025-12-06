#!/bin/bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/local}"
cd "${ROOT}" || exit 1
source "./local/_includes.sh"

DOCS_DIR="docs"
INDEX_FILE="$DOCS_DIR/index.html"

echoH1 "📄 Generating HTML documentation"
echo
echo "💾 Result will be: $INDEX_FILE"
echo "🌍 GitHub pages URL: https://seepick.github.io/diamond/"

./gradlew :doc:SoftwareDocument:clean :doc:SoftwareDocument:asciidoctor || exit 1
cp -r doc/SoftwareDocument/build/docs/asciidoc/ $DOCS_DIR/ || exit 1
echoDocCommitWarning
open $INDEX_FILE || exit 1
echoSuccess "Generating HTML documentation"
