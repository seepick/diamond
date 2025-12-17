FROM amazoncorretto:22 AS runtime
EXPOSE 8080
RUN mkdir /app
COPY build/libs/demo-app-all.jar /app/demo-app.jar
ENTRYPOINT ["java","-jar","/app/demo-app.jar"]
