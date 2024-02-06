FROM maven:3-eclipse-temurin-17 AS build
COPY . .
RUN mvn clean package -Dmaven.test.skip=true
FROM eclipse-temurin:17-alpine
COPY --from=build /target/pictweet-3.1.8.jar pictweet.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "pictweet.jar"]