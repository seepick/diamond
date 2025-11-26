#!/bin/bash

./../../gradlew :doc:SoftwareArchitectureDocument:asciidoctorPdf || exit 1
open build/docs/asciidocPdf/index.pdf || exit 1

