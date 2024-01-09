FROM openjdk:17-oracle
COPY target/*.jar api.jar
ENTRYPOINT ["java","-jar","api.jar"]