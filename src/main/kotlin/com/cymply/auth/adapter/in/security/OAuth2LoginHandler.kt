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
        const val ACCESS_TOKEN_KEY = "access_token"
        const val REFRESH_TOKEN_KEY = "refresh_token"
    }

    override fun onAuthenticationSuccess(
        servletRequest: HttpServletRequest,
        servletResponse: HttpServletResponse,
        authentication: Authentication,
    ) {
        val token = authentication as OAuth2AuthenticationToken
        val principal = token.principal as OAuth2User
        val registration = token.authorizedClientRegistrationId

        val request = OAuth2LoginRequest(principal.attributes, registration)
        val command = request.toOAuth2LoginCommand()
        val result = oauth2LoginUseCase.oAuth2Login(command)
        servletResponse.status = HttpStatus.OK.value()

        var path = when (result.scopes?.firstOrNull()) {
            "user:signup" -> "$client/signup/step0"
            else -> "$client/signin"
        }

        path = "$path?$ACCESS_TOKEN_KEY=${result.accessToken}&$REFRESH_TOKEN_KEY=${result.refreshToken}"
        servletResponse.sendRedirect(path)
    }

    private fun setCookie(response: HttpServletResponse, key: String, value: String) {
        val cookie = "$key=$value; Max-Age=60; Path=/; HttpOnly; SameSite=Lax"
        response.addHeader("Set-Cookie", cookie)
    }
}

@Component
class OAuth2FailureHandler : SimpleUrlAuthenticationFailureHandler() {
    override fun onAuthenticationFailure(
        request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException?
    ) {
        response.status = HttpStatus.UNAUTHORIZED.value()
    }
}