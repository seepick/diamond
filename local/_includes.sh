#!/bin/bash

# GENERAL VAL
#############################################################################

COL_RED="\033[0;31m"
COL_GREEN="\033[0;32m"
COL_RESET="\033[0m"

#Black	0;30
#Blue	0;34
#Cyan	0;36
#Purple	0;35
#Brown	0;33
#Blue	0;34
#Green	0;32
#Cyan	0;36
#Red	0;31
#Purple	0;35
#Brown	0;33

# GENERAL FUN
#############################################################################

echoH1() {
  TITLE=$1
  echo "==================================================="
  echo "=== ${TITLE}"
  echo "==================================================="
}

echoSuccess() {
  TITLE=$1
  echo ""
  echo -e "✅ $TITLE was ${COL_GREEN}SUCCESSFUL${COL_RESET} ✅"
  echo ""
}

verifyExists() {
  FILE=$1
  if [ ! -f "$FILE" ]; then
      echo "$FILE does not exist!"
      exit 1
  fi
}

# SPECIFIC FUN
#############################################################################

echoDocCommitWarning() {
  echo ""
  echo -e "⚠️ ${COL_RED}Commit changes${COL_RESET} to trigger GitHub pages action. ⚠️"
  sleep 1
  echo ""
}
