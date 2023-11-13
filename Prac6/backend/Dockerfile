FROM openjdk:17-oracle
RUN microdnf update && microdnf install -y maven
WORKDIR backend
COPY pom.xml /backend/
COPY src/ /backend/src/
RUN mvn clean install
