# ビルドステージ
FROM gradle:7.3.3-jdk11 AS build
WORKDIR /app
COPY --chown=gradle:gradle . /app
# RUN gradle clean build -x test
RUN gradle clean build -x test --stacktrace

# 実行ステージ
FROM openjdk:11-jre-slim
WORKDIR /app

# ビルドステージからビルド成果物をコピー
COPY --from=build /app/build/libs/pictweet-0.0.1-SNAPSHOT.jar /app/pictweet.jar

# 実行コマンド
ENTRYPOINT ["java", "-jar", "/app/pictweet.jar"]
