# demo app

A simple webservice returning "Hello World!" and the environment variables on the root `/` path. It runs on port 8080
and provides a containerized image tagged `demo-app:latest`.

## Building and containerizing

* `./gradlew build` for fat-jar assembly at: `build/libs/demo-app-all.jar`
* Containerize via: `docker build -t demo-app:latest .`

## Run with Docker

* start: `docker run --rm -p 8080:8080 demo-app:latest` && `wget localhost:8080`
* stop: look up container ID: `docker ps` and then `docker stop <container_id>`
