package com.cymply.auth.config

import com.cymply.auth.adapter.`in`.security.dto.PrincipalDetail
import com.cymply.common.util.JwtUtils
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2LoginSuccessHandler(
    val jwtUtils: JwtUtils
) : SimpleUrlAuthenticationSuccessHandler() {
    companion object {
        const val ACCESS_TOKEN_KEY = "AccessToken"
        const val REFRESH_TOKEN_KEY = "RefreshToken"
        const val ACCESS_TOKEN_EXPIRES = 1800_000.toLong()  // 30 min
        const val REFRESH_TOKEN_EXPIRES = 604_800_000.toLong()  // 7 days
        const val TEMPORAL_TOKEN_EXPIRES = 900_000.toLong()  // 15 min
    }

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val principal = authentication.principal as PrincipalDetail

        if (!principal.authorities.contains(SimpleGrantedAuthority("USER"))) {
            val accessToken = jwtUtils.generate(principal.attributes, REFRESH_TOKEN_EXPIRES)
            setCookie(response, ACCESS_TOKEN_KEY, accessToken)
            response.status = HttpStatus.OK.value()
            response.sendRedirect("http://localhost:3000/signup")
        } else {
            /**
             * TODO
             * Refresh Token 저장
             */
            val accessToken = jwtUtils.generate(principal.attributes, ACCESS_TOKEN_EXPIRES)
            val refreshToken = jwtUtils.generate(principal.attributes, REFRESH_TOKEN_EXPIRES)
            setCookie(response, ACCESS_TOKEN_KEY, accessToken)
            setCookie(response, REFRESH_TOKEN_KEY, refreshToken)
            response.status = HttpStatus.OK.value()
            response.sendRedirect("http://localhost:3000/")
        }
    }

    private fun setCookie(response: HttpServletResponse, key: String, value: String) {
        val cookie = Cookie(key, value)
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.maxAge = 60
        response.addCookie(cookie)
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