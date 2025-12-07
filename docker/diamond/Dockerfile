# see /bin/build_image.sh

FROM amazoncorretto:22 AS runtime
EXPOSE 8080
RUN mkdir /app
COPY ../../app/build/libs/diamond.jar /app/diamond.jar
RUN mkdir /app/config
COPY ../../app/src/main/docker/* /app/config/
ENTRYPOINT ["java","-jar","/app/diamond.jar"]
