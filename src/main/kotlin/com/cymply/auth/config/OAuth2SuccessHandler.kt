package com.cymply.auth.config

import com.cymply.auth.adapter.`in`.security.PrincipalDetail
import com.cymply.common.util.JwtUtils
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class OAuth2SuccessHandler(
    val jwtUtils: JwtUtils
) : SimpleUrlAuthenticationSuccessHandler() {
    companion object {
        const val ACCESS_TOKEN_KEY = "AccessToken"
        const val REFRESH_TOKEN_KEY = "RefreshToken"
        const val ACCESS_TOKEN_EXPIRES = (60 * 60 * 1000).toLong()  // 1 hour
        const val REFRESH_TOKEN_EXPIRES = (7 * 24 * 60 * 60 * 1000).toLong()  // 7 days
        const val TEMPORAL_TOKEN_EXPIRES = (10 * 60 * 1000).toLong()  // 10 min
    }

    override fun onAuthenticationSuccess(
        request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication
    ) {
        val principal = authentication.principal as PrincipalDetail

        if (!principal.authorities.contains(SimpleGrantedAuthority("ROLE_USER"))) {
            val at = jwtUtils.generate(principal.attributes, TEMPORAL_TOKEN_EXPIRES)
            setCookie(response, ACCESS_TOKEN_KEY, at)
            response.status = HttpStatus.OK.value()
            response.sendRedirect("http://localhost:3000/signup")
        } else {
            val at = jwtUtils.generate(principal.attributes, ACCESS_TOKEN_EXPIRES)
            val rt = jwtUtils.generate(principal.attributes, REFRESH_TOKEN_EXPIRES)

            setCookie(response, ACCESS_TOKEN_KEY, at)
            setCookie(response, REFRESH_TOKEN_KEY, rt)
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
