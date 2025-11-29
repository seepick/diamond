#!/bin/bash

cd ..
./gradlew :app:assemble

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

EXPECTED_KTOR="ktor=KtorConfig(port=12)"
EXPECTED_DB="database=DatabaseConfig(stubEnabled=false, jdbcUrl=db_url, username=db_user, password=****)"
EXPECTED_EXTERN_SFTP="sftp=SftpConfig(remoteHost=sftpHost, port=22, username=sftpUser, authIsPassword=true, authPasswordOrPrivateKeyPath=sftpPass, knownHostsFilePath=knownHosts, strictHostChecking=true)"
EXPECTED_EXTERN="extern=ExternConfig(postsServiceBaseUrl=postsUrl, $EXPECTED_EXTERN_SFTP)"
EXPECTED="EnvConfig($EXPECTED_KTOR, $EXPECTED_DB, $EXPECTED_EXTERN)"

if [ "$OUTPUT" = "$EXPECTED" ]; then
  echo "All OK ✅"
else
  echo "Configuration did not match expected value ❌"
  echo ""
  echo "ACTUAL>>   $OUTPUT"
  echo "EXPECTED>> $EXPECTED"
  exit 1
fi
