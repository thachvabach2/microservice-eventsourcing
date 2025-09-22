# Stage 1: Build the application
FROM gradle:8.7-jdk17 AS build
WORKDIR /app

COPY gradle/libs.versions.toml ./gradle/
COPY build.gradle.kts .
COPY settings.gradle.kts .

COPY discoverserver/ ./discoverserver/

#skip task: test
RUN gradle :discoverserver:clean :discoverserver:build -x test --no-daemon

# Stage 2: use OpenJDK for running
FROM openjdk:17-slim
WORKDIR /app

RUN apt-get update && apt-get install -y curl && apt-get clean && rm -rf /var/lib/apt/lists/*
COPY --from=build /app/discoverserver/build/libs/*.jar discoverserver.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "discoverserver.jar"]