#!/bin/bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/bin}"
cd "${ROOT}" || exit 1
source "./bin/_includes.sh"

DOCS_DIR="docs"
INDEX_FILE="$DOCS_DIR/index.html"

echoH1 "📄 Generating HTML documentation"
echoParam "💾 Result will be" $INDEX_FILE
echoParam "🌍 GitHub pages URL" "https://seepick.github.io/diamond/"
echo ""

./gradlew :doc:SoftwareDocument:clean :doc:SoftwareDocument:asciidoctor || exit 1
cp -r doc/SoftwareDocument/build/docs/asciidoc/ $DOCS_DIR/ || exit 1
echoDocCommitWarning
open $INDEX_FILE || exit 1
echoSuccess "Generating HTML documentation"
