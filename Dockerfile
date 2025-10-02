FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy the built JAR file
COPY target/microuser.jar microuser.jar

# Expose the port your app runs on
EXPOSE 8091

# Run the application
ENTRYPOINT ["java", "-jar", "microuser.jar"]