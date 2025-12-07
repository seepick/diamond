#!/bin/bash

# ensure CWD is project root
CWD=`pwd`
ROOT="${CWD%/bin}"
cd "${ROOT}" || exit 1
source "./bin/_includes.sh"

echoH1 "🔧 Testing application configuration"
echo ""

./gradlew :app:assemble || exit 1

# see: https://seepick.github.io/diamond/#environment-variables
export DATABASE_JDBCURL=db_url
export DATABASE_USERNAME=db_user
export DATABASE_PASSWORD=db_pass
export EXTERN_POSTSSERVICEBASEURL=postsUrl
export EXTERN_SFTP_REMOTEHOST=sftpHost
export EXTERN_SFTP_USERNAME=sftpUser
export EXTERN_SFTP_AUTHISPASSWORD=true
export EXTERN_SFTP_AUTHPASSWORDORPRIVATEKEYPATH=sftpPass
export EXTERN_SFTP_KNOWNHOSTSFILEPATH=knownHosts
export KTOR_PORT=12

OUTPUT=$(java -jar app/build/libs/diamond.jar printConfigOnly)

EXPECTED_KTOR="ktor=KtorConfig(port=$KTOR_PORT)"
EXPECTED_DB="database=DatabaseConfig(jdbcUrl=db_url, username=db_user, password=****)"
EXPECTED_EXTERN_SFTP="sftp=SftpConfig(remoteHost=sftpHost, port=22, username=sftpUser, authIsPassword=true, authPasswordOrPrivateKeyPath=****, knownHostsFilePath=knownHosts, strictHostChecking=true)"
EXPECTED_EXTERN="extern=ExternConfig(postsServiceBaseUrl=postsUrl, $EXPECTED_EXTERN_SFTP)"
EXPECTED="EnvConfig($EXPECTED_KTOR, $EXPECTED_DB, $EXPECTED_EXTERN)"


if [ "$OUTPUT" = "$EXPECTED" ]; then
  echoSuccess "🔧 Testing application configuration"
else
  echo -e "❌ ${COL_RED}Configuration did not match expected value!${COL_RESET} ❌"
  echo ""
  echo "ACTUAL>>   $OUTPUT"
  echo "EXPECTED>> $EXPECTED"
  exit 1
fi
