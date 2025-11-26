#!/bin/bash

cd ..
./gradlew :doc:SoftwareArchitectureDocument:asciidoctorPdf || exit 1
open doc/SoftwareArchitectureDocument/build/docs/asciidocPdf/index.pdf || exit 1
