FROM adoptopenjdk/openjdk11:alpine-jre

COPY . ./app

WORKDIR /app

EXPOSE 8080
EXPOSE 9090

ENTRYPOINT ["java", "-jar", "./target/user_service.jar"]

