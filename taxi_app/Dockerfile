FROM adoptopenjdk/openjdk11:alpine-jre

COPY . ./app

WORKDIR /app

EXPOSE 8090
EXPOSE 9090

ENTRYPOINT ["java", "-jar", "./target/taxi_app.jar"]