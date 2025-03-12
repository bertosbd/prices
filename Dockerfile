# Use Java 17 base image
FROM eclipse-temurin:17-jre-alpine-3.21

# Set the working directory
WORKDIR /app

# Copy the built jar file into the container
COPY target/price-0.0.1-SNAPSHOT.jar price-api.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "price-api.jar"]