package com.cymply.auth.adapter.`in`.security

import com.cymply.auth.application.port.`in`.LoginUseCase
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component

@Component
class CustomOAuth2UserService(
    private val loginUseCase: LoginUseCase
) : DefaultOAuth2UserService() {
    override fun loadUser(request: OAuth2UserRequest): OAuth2User {
        val oAuth2User = super.loadUser(request)
        val command = OAuth2LoginCommandConverter.convert(request, oAuth2User)
        loginUseCase.oAuth2Login(command)
        return oAuth2User
    }
}
