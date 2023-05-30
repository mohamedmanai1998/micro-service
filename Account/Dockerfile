FROM openjdk:17-jdk-slim-buster
WORKDIR /opt
COPY target/account-0.0.1-SNAPSHOT.jar account-0.0.1-SNAPSHOT.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "account-0.0.1-SNAPSHOT.jar"]
