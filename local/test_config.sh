#!/bin/bash

cd ..
./gradlew :app:assemble

export DATABASE_JDBCURL=db_url
export DATABASE_USERNAME=db_user
export DATABASE_PASSWORD=db_pass
export KTOR_PORT=12

OUTPUT=$(java -jar app/build/libs/diamond.jar printConfigOnly)
echo "$OUTPUT"
EXPECTED="Config(ktor=KtorConfig(port=12), database=DatabaseConfig(stubEnabled=false, url=db_url, username=db_user, password=****))"

if [ "$OUTPUT" = "$EXPECTED" ]; then
  echo "All OK ✅"
else
  echo "Configuration did not match expected value ❌"
  echo "$EXPECTED"
  exit 1
fi


