FROM openjdk:17-jdk-slim-buster
WORKDIR /opt/app
COPY target/Eureka-Server-1.0.jar Eureka-Server-1.0.jar
EXPOSE 8762
ENTRYPOINT ["java", "-jar", "Eureka-Server-1.0.jar"]