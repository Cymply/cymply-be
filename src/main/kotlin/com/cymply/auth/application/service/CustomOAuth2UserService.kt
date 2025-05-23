package com.cymply.auth.application.service

import com.cymply.auth.application.port.out.RegisterUserPort
import com.cymply.user.adapter.`in`.auth.RegisterUserRequest
import com.cymply.user.application.port.out.LoadUserPort
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class CustomOAuth2UserService(
    private val loadUserPort: LoadUserPort,
    private val registerUserPort: RegisterUserPort
) : DefaultOAuth2UserService() {
    /**
     * 회원 조회 및 검증
     * 1. 소셜 플랫폼에서 전달받은 개인 정보를 통해 검증
     * 2. 일치하는 이메일이 없는 경우 회원 정보를 저장
     */
    override fun loadUser(request: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(request)
        val oAuth2UserInfo = OAuth2UserInfo.of(request, oAuth2User)
        val user = loadUserPort.loadUserByEmail(oAuth2UserInfo.email)

        if (user == null) {
            registerUserPort.registerUser(
                RegisterUserRequest(
                    email = oAuth2UserInfo.email,
                    provider = oAuth2UserInfo.provider,
                    sub = oAuth2UserInfo.id,
                    name = oAuth2UserInfo.nickname,
                    nickname = oAuth2UserInfo.nickname,
                    thumbnail = null
                )
            )
        }
        user?.verifyValidUser(oAuth2UserInfo.provider)
        return oAuth2User
    }
}


