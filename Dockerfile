FROM openjdk:17-jdk-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle build files
COPY build.gradle .
COPY settings.gradle .
COPY gradlew .
COPY gradle gradle

# Download and cache the Gradle distribution
RUN ./gradlew --version

# Copy the source code
COPY . .

# Build the application using Gradle
RUN ./gradlew build -x test

# Create a minimal JRE-based image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/build/libs/vehicle-0.0.1-SNAPSHOT.jar .

# Set the entrypoint to launch the application
ENTRYPOINT ["java", "-jar", "vehicle-0.0.1-SNAPSHOT.jar"]