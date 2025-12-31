#!/bin/bash

# to deploy image so that it's available to minikube
eval $(minikube docker-env)

./gradlew build && docker build -t demo-app:3 .
