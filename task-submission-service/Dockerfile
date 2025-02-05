FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21

WORKDIR /app

COPY --from=build /app/target/task-submission-service-0.0.1-SNAPSHOT.jar .

EXPOSE 5454

CMD ["java","-jar","task-submission-service-0.0.1-SNAPSHOT.jar"]
