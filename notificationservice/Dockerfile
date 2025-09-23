# Stage 1: Build the application
FROM gradle:8.7-jdk17 AS build
WORKDIR /app

COPY gradle/libs.versions.toml ./gradle/
COPY build.gradle.kts .
COPY settings.gradle.kts .

COPY notificationservice/ ./notificationservice/
COPY commonservice/ ./commonservice/

#skip task: test
RUN gradle :notificationservice:clean :notificationservice:build -x test --no-daemon

# Stage 2: use OpenJDK for running
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/notificationservice/build/libs/*.jar notificationservice.jar
EXPOSE 9004
ENTRYPOINT ["java", "-jar", "notificationservice.jar"]