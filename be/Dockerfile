FROM openjdk:17-jdk-slim-buster

WORKDIR /app

COPY . /app

RUN apt-get update && \
    apt-get install -y --no-install-recommends curl && \
    rm -rf /var/lib/apt/lists/* && \
    ./gradlew build && \
    rm -rf /root/.gradle

EXPOSE 8080

CMD ["java", "-jar", "build/libs/*.jar"]
