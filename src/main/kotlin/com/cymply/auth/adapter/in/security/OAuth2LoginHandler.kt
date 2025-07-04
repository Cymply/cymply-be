package com.cymply.auth.adapter.`in`.security

import com.cymply.auth.adapter.`in`.dto.OAuth2LoginRequest
import com.cymply.auth.application.port.`in`.OAuth2LoginUseCase
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2LoginSuccessHandler(
    @Value("\${spring.app.client}")
    private val client: String,
    private val oauth2LoginUseCase: OAuth2LoginUseCase

) : SimpleUrlAuthenticationSuccessHandler() {

    companion object {
        const val ACCESS_TOKEN_KEY = "AccessToken"
        const val REFRESH_TOKEN_KEY = "RefreshToken"
    }

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication,
    ) {
        val oAuth2AuthenticationToken = authentication as OAuth2AuthenticationToken
        val principal = oAuth2AuthenticationToken.principal as OAuth2User
        val registration = oAuth2AuthenticationToken.authorizedClientRegistrationId

        val oAuth2LoginRequest = OAuth2LoginRequest(principal.attributes, registration)
        val oAuth2LoginCommand = oAuth2LoginRequest.toOAuth2LoginCommand()
        val result = oauth2LoginUseCase.oAuth2Login(oAuth2LoginCommand)

        setCookie(response, ACCESS_TOKEN_KEY, result.accessToken)
        setCookie(response, REFRESH_TOKEN_KEY, result.refreshToken)
        response.status = HttpStatus.OK.value()

        val redirect = when (result.scopes?.firstOrNull()) {
            "user:signup" -> "${client}/signup"
            else -> "${client}/"
        }
        response.sendRedirect(redirect)
    }

    private fun setCookie(response: HttpServletResponse, key: String, value: String) {
        val cookieValue = "$key=$value; Max-Age=60; Path=/; HttpOnly; SameSite=Lax"
        response.addHeader("Set-Cookie", cookieValue)
    }
}

@Component
class OAuth2FailureHandler(
) : SimpleUrlAuthenticationFailureHandler() {
    override fun onAuthenticationFailure(
        request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException?
    ) {
        response.status = HttpStatus.UNAUTHORIZED.value()
    }
}