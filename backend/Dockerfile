FROM gradle:jdk21 AS builder

WORKDIR /app
COPY . /app

RUN gradle build --no-daemon

FROM openjdk:21-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
