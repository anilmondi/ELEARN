# Use a Java 17 image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy everything into the image
COPY . .

# Build the app
RUN ./mvnw clean install

# Run the app
CMD ["java", "-jar", "target/user-service-0.0.1-SNAPSHOT.jar"]
