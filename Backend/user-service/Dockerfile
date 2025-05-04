FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
RUN ./mvnw dependency:go-offline

COPY src src
RUN ./mvnw package -DskipTests

EXPOSE 8081

CMD ["java", "-jar", "target/user-service-0.0.1-SNAPSHOT.jar"]
