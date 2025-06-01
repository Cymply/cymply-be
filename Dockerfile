# jdk 베이스 이미지
FROM openjdk:21-jdk

# 작업 디렉터리 지정
WORKDIR /usr/src/app

# 파일 복사
COPY build/libs/*SNAPSHOT.jar app.jar

# 프로그램 실행 포트 문서화
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app.jar"]