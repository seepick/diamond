* use java-time or kotlin-time?
* java:
    * GOOD: robust, well-known, well-integrated with libs (serialization, DB)
    * BAD: cumberesome
    * BAD: not supported by kotlin-idiomatic libs
* kotlin:
    * GOOD: lightweight, probably/most-likely kotlin idiomatic; modern API
    * GOOD: integration with kotlinx-serialization and exposed
    * BAD: not all (java) libs support it
    * BAD: higher barrier of entry for others
    * any benefit from its multiplatform nature?
* decision go for kotlin, let's see if hit a wall
