FROM gradle:8.5-jdk21 AS builder

WORKDIR /app

# Copy build files
COPY build.gradle.kts settings.gradle.kts ./
COPY gradlew* ./
COPY gradle/ ./gradle/

# Copy source code
COPY src/ ./src/

# Build the application (this will regenerate proto files and compile)
RUN gradle build --no-daemon -x test

# Runtime stage: copy the built JAR
FROM eclipse-temurin:21-jre

RUN apt-get update && apt-get install -y --no-install-recommends curl \
    && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy the built JAR from builder stage
COPY --from=builder /app/build/libs/*[0-9].jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]