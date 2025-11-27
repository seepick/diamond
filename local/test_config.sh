#!/bin/bash

cd ..
./gradlew :app:assemble

export DATABASE_JDBCURL=db_url
export DATABASE_USERNAME=db_user
export DATABASE_PASSWORD=db_pass
export EXTERN_POSTSSERVICEBASEURL=postsUrl
export KTOR_PORT=12

OUTPUT=$(java -jar app/build/libs/diamond.jar printConfigOnly)
echo "$OUTPUT"
EXPECTED="EnvConfig(ktor=KtorConfig(port=12), database=DatabaseConfig(stubEnabled=false, jdbcUrl=db_url, username=db_user, password=****), extern=ExternConfig(postsServiceBaseUrl=postsUrl))"

if [ "$OUTPUT" = "$EXPECTED" ]; then
  echo "All OK ✅"
else
  echo "Configuration did not match expected value ❌"
  echo "ACTUAL: $OUTPUT"
  echo "EXPECTED: $EXPECTED"
  exit 1
fi
