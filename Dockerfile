# Multi-stage build for Najaspad application
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

# Copy pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Install curl for healthchecks
RUN apk add --no-cache curl

# Create a non-root user to run the application
RUN addgroup -S spring && adduser -S spring -G spring

# Copy the built jar from build stage
COPY --from=build /app/target/*.jar app.jar
RUN chown spring:spring app.jar

USER spring:spring

# Expose port 8080
EXPOSE 8080

# Set JVM options for container environment
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

