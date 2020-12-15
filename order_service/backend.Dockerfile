FROM adoptopenjdk/openjdk11:alpine-jre

COPY . ./app

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "./target/order_service.jar"]