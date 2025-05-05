FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

COPY mvnw . 
COPY .mvn .mvn 
COPY pom.xml . 
RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src src 
RUN ./mvnw clean package -DskipTests

EXPOSE 8084

CMD ["java", "-jar", "target/purchasehistory-service-0.0.1-SNAPSHOT.jar"]
