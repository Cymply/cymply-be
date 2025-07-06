# 소셜 로그인

## 1. 로그인

```mermaid 
sequenceDiagram
    autonumber
    participant FRONTEND
    participant BACKEND
    participant RESOURCE_SERVER
    participant RDB
    FRONTEND -->> + BACKEND: 로그인 요청 /oauth2/authorize/{플랫폼 아이디}
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
        BACKEND -->> FRONTEND: HTTP 302(/signup) + 임시 AccessToken
    else 회원인 경우
        BACKEND -->>  - FRONTEND: HTTP 302(/) + AccessToken, RefreshToken
    end 
```

- 소셜 플랫폼 ID
    - 구글: google
    - 카카오: kakao

- 참고
    - https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api

## 2. 회원가입

- **로그인 요청 시 반환받은 AccessToken을 Header에 포함
- 로그인 요청 시 반환받은 AccessToken은 접근 범위가 제한적
    - 닉네임 검사, 회원가입, 정상 토큰 발급만 접근 가능

```mermaid 
sequenceDiagram
    autonumber
    participant FRONTEND
    participant BACKEND
    FRONTEND -->> + BACKEND: 1. 닉네임 검사 /api/v1/users/check/nickname/{닉네임}
    BACKEND -->> + RDB: 닉네임 조회
    RDB -->> - BACKEND: 닉네임 반환
    alt 사용 가능한 경우
        BACKEND -->> FRONTEND: success=true 응답
    else 사용 불가능한 경우
        BACKEND -->>  - FRONTEND: success=false 응답
    end

    FRONTEND -->> + BACKEND: 2. 회원가입 /api/v1/users/signup/oauth2
    BACKEND -->> + RDB: 회원 저장
    RDB -->> - BACKEND: 회원 반환
    alt 회원가입 성공한 경우
        BACKEND -->> FRONTEND: success=true 응답
    else 회원가입 실패한 경우
        BACKEND -->>  - FRONTEND: success=false 응답
    end

    FRONTEND -->> + BACKEND: 3. 정상 토큰 요청 /api/v1/users/signup/oauth2/success
    BACKEND -->> - FRONTEND: 정상 토는 응답 + AccessToken, RefreshToken

```

