FROM openjdk:21-jdk-slim
WORKDIR /app
COPY target/chefbook-0.0.1-SNAPSHOT.jar chefbook.jar
ENTRYPOINT ["java", "-jar", "chefbook.jar"]