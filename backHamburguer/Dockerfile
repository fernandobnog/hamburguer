FROM openjdk:17
MAINTAINER Fernando Nogueira

WORKDIR /hamburguerApp

COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline

COPY src ./src

CMD ["./mvnw", "spring-boot:run"]