# 소셜 로그인

## 1. 시퀀스다이어그램

```mermaid 
sequenceDiagram
    autonumber
    participant FRONTEND
    participant BACKEND
    participant RESOURCE_SERVER
    participant RDB
    FRONTEND -->> + BACKEND: 로그인 요청
    BACKEND -->>  - FRONTEND: 인가 코드 발급 요청/HTTP 302
    FRONTEND -->> RESOURCE_SERVER: 인가 코드 발급 요청(+redirect_uri)
    RESOURCE_SERVER -->> FRONTEND: 로그인 페이지/HTTP 302
    FRONTEND -->> RESOURCE_SERVER: 소셜 계정 로그인
    RESOURCE_SERVER -->> FRONTEND: 사용자 동의 요청
    FRONTEND -->> RESOURCE_SERVER: 사용자 동의 완료
    RESOURCE_SERVER -->> + BACKEND: 인가 코드 전달/HTTP 302
    BACKEND -->> RESOURCE_SERVER: 토큰 요청
    RESOURCE_SERVER -->> BACKEND: 토큰 발급
    BACKEND -->> RESOURCE_SERVER: 사용자 정보 요청
    RESOURCE_SERVER -->> BACKEND: 사용자 정보 반환
    BACKEND -->> RDB: 회원 정보 조회
    RDB -->> BACKEND: 회원 정보 반환

    alt 비회원인 경우
        BACKEND -->> FRONTEND: 회원가입 페이지/HTTP 302(/signup)
    else 회원인 경우
        BACKEND -->>  - FRONTEND: 로그인 성공, 실패/HTTP 302(/)
    end 
```

### 로그인 요청

#### [GET] /oauth2/authorize/{소셜 플랫폼 ID}

- 소셜 플랫폼 ID
    - 구글: google
    - 카카오: kakao

- 참고
    - https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api
