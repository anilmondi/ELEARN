FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN ./mvnw clean install

CMD ["java", "-jar", "target/api-gateway-0.0.1-SNAPSHOT.jar"]
