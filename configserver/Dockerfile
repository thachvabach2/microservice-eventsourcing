FROM gradle:8.7-jdk17 AS build
WORKDIR /app

COPY gradle/libs.versions.toml ./gradle/
COPY build.gradle.kts .
COPY settings.gradle.kts .

COPY configserver/ ./configserver/

#skip task: test
RUN gradle :configserver:clean :configserver:build -x test --no-daemon

FROM openjdk:17-slim
WORKDIR /app

RUN apt-get update && apt-get install -y curl && apt-get clean && rm -rf /var/lib/apt/lists/*
COPY --from=build /app/configserver/build/libs/*.jar configserver.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "configserver.jar"]