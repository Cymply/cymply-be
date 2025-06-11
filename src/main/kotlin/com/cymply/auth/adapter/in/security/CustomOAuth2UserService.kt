package com.cymply.auth.adapter.`in`.security

import com.cymply.user.application.port.`in`.GetUserUseCase
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Component

@Component
class CustomOAuth2UserService(
    private val getUserUseCase: GetUserUseCase,
) : DefaultOAuth2UserService() {
    override fun loadUser(request: OAuth2UserRequest): OAuth2User {
        val oauth = super.loadUser(request)
        val provider = request.clientRegistration.registrationId.uppercase()
        val account = OAuth2UserAccount.from(provider, oauth)
        getUserUseCase.getActiveUser(sub = oauth.name, provider = provider)
            ?: return PrincipalDetail(account.getAttributes())

        return PrincipalDetail(account.getAttributes())
    }
}
