# Multi-stage build for Najaspad application
FROM maven:3.9-eclipse-temurin-17-alpine AS build

WORKDIR /app

# Copy pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage (distroless, much smaller than full JRE images)
FROM gcr.io/distroless/java17-debian12:nonroot

WORKDIR /app

# Copy the built jar from build stage
COPY --from=build /app/target/*.jar /app/app.jar

# Expose port 8080
EXPOSE 8080

# Run the application (no shell in distroless)
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
