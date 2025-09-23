# Stage 1: Build the application
FROM gradle:8.7-jdk17 AS build
WORKDIR /app

COPY gradle/libs.versions.toml ./gradle/
COPY build.gradle.kts .
COPY settings.gradle.kts .

COPY apigateway/ ./apigateway/

#skip task: test
RUN gradle :apigateway:clean :apigateway:build -x test --no-daemon

# Stage 2: use OpenJDK for running
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/apigateway/build/libs/*.jar apigateway.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "apigateway.jar"]