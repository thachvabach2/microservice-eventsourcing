FROM gradle:8.7-jdk17 AS build
WORKDIR /app

COPY gradle/libs.versions.toml ./gradle/
COPY build.gradle.kts .
COPY settings.gradle.kts .

COPY bookservice/ ./bookservice/
COPY commonservice/ ./commonservice/

#skip task: test
RUN gradle :bookservice:clean :bookservice:build -x test --no-daemon

# Stage 2: use OpenJDK for running
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/bookservice/build/libs/*.jar bookservice.jar
EXPOSE 9002
ENTRYPOINT ["java", "-jar", "bookservice.jar"]