FROM openjdk:17-jdk-slim-buster
WORKDIR /opt/app
COPY target/ConfigServer-0.0.1-SNAPSHOT.jar ConfigServer-0.0.1-SNAPSHOT.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "ConfigServer-0.0.1-SNAPSHOT.jar"]