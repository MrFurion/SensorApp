FROM maven:3.8.3-openjdk-17 AS builder

WORKDIR /build

COPY mvnw pom.xml ./
COPY .mvn/ .mvn
RUN ./mvnw dependency:go-offline

COPY ./src ./src
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:17-jre-alpine

WORKDIR /opt/app

COPY --from=builder /build/target/*.jar /opt/app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]
