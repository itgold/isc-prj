FROM west-1.amazonaws.com/openjdk:12.0.2-jdk

WORKDIR /app

COPY artifacts/application.jar application.jar

ENTRYPOINT ["java", "-jar", "/app/application.jar"]
