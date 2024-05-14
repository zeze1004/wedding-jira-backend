# 1단계: 빌드 환경
FROM openjdk:17-slim as build
WORKDIR /workspace/app

# Gradle Wrapper & 소스 코드 복사
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# 애플리케이션 빌드
RUN ./gradlew bootJar -x test

# 2단계: 최종 이미지
FROM openjdk:17-slim
WORKDIR /app

# 빌드 스테이지에서 생성된 JAR 파일 복사
COPY --from=build /workspace/app/build/libs/wedding-0.0.1-SNAPSHOT.jar app.jar

# init.sql 파일 복사
COPY init.sql /docker-entrypoint-initdb.d/init.sql

# 애플리케이션 실행 시 dev 프로파일 활성화
ENTRYPOINT ["java", "-Dspring.profiles.active=dev", "-jar", "app.jar"]
