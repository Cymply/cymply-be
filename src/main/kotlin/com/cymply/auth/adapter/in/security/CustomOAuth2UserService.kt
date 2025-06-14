package com.cymply.auth.adapter.`in`.security

import com.cymply.auth.adapter.`in`.security.dto.GooglePrincipalDetail
import com.cymply.auth.adapter.`in`.security.dto.KakaoPrincipalDetail
import com.cymply.user.application.port.`in`.GetUserUseCase
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component

@Component
class CustomOAuth2UserService(
    private val getUserUseCase: GetUserUseCase
) : DefaultOAuth2UserService() {
    override fun loadUser(request: OAuth2UserRequest): OAuth2User? {
        val oauth2User = super.loadUser(request)
        val provider = request.clientRegistration.registrationId.uppercase()
        val user = getUserUseCase.getActiveUser(provider, oauth2User.name)

        val id = user?.id ?: -1L
        val role = user?.role?.name ?: "PENDING_USER"

        return when (provider) {
            "GOOGLE" -> GooglePrincipalDetail.from(id, role, oauth2User)
            "KAKAO" -> KakaoPrincipalDetail.from(id, role, oauth2User)
            else -> throw IllegalArgumentException("Unsupported provider: $provider")
        }
    }
}
