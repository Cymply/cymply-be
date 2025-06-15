package com.cymply.auth.config

import com.cymply.auth.adapter.`in`.security.PrincipalDetail
import com.cymply.common.util.JwtUtils
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2SuccessHandler(
    val jwtUtils: JwtUtils,

    @Value("\${spring.jwt.keys.access}")
    private val accessTokenKey: String,

    @Value("\${spring.jwt.keys.refresh")
    private val refreshTokenKey: String,

    @Value("\${spring.jwt.expires.access}")
    private val accessTokenExpires: Long,

    @Value("\${spring.jwt.expires.refresh}")
    private val refreshTokenExpires: Long,

    @Value("\${spring.jwt.expires.temporal}")
    private val temporalTokenExpires: Long,

    @Value("\${spring.jwt.cookie.max-age}")
    private val cookieMaxAge: Int,

) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication
    ) {
        val principal = authentication.principal as PrincipalDetail

        if (!principal.authorities.contains(SimpleGrantedAuthority("ROLE_USER"))) {
            val at = jwtUtils.generate(principal.attributes, temporalTokenExpires)
            setCookie(response, accessTokenKey, at)
            response.status = HttpStatus.OK.value()
            response.sendRedirect("http://localhost:3000/signup")
        } else {
            val at = jwtUtils.generate(principal.attributes, accessTokenExpires)
            val rt = jwtUtils.generate(principal.attributes, refreshTokenExpires)

            setCookie(response, accessTokenKey, at)
            setCookie(response, refreshTokenKey, rt)
            response.status = HttpStatus.OK.value()
            response.sendRedirect("http://localhost:3000/")
        }
    }

    private fun setCookie(response: HttpServletResponse, key: String, value: String) {
        val cookie = Cookie(key, value)
        cookie.path = "/"
        cookie.isHttpOnly = true
        cookie.maxAge = cookieMaxAge
        response.addCookie(cookie)
    }
}
