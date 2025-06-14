FROM gradle:jdk21 AS builder

WORKDIR /app
COPY build.gradle settings.gradle /app/
COPY gradle /app/gradle
# 의존성만 먼저 다운로드하여 캐시 활용
RUN gradle dependencies --no-daemon

COPY . /app
RUN gradle build --no-daemon

FROM openjdk:21-slim

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
