FROM openjdk:17-alpine
CMD ["./mvnw", "clean", "package"]
WORKDIR /usr/app
ARG JAR_FILE_PATH=build/libs/Project-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE_PATH} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]