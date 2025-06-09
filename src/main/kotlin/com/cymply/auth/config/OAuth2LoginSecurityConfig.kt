package com.cymply.auth.config

import com.cymply.auth.adapter.`in`.security.PrincipalDetail
import com.cymply.auth.adapter.`in`.security.CustomOAuth2UserService
import com.cymply.common.util.JwtUtils
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Configuration
class OAuth2LoginSecurityConfig(
    private val oAuth2UserService: CustomOAuth2UserService,
    private val oAuth2SuccessHandler: OAuth2SuccessHandler,
    private val oAuth2FailureHandler: OAuth2FailureHandler,
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors { CorsConfig().corsConfigurationSource() }
            .authorizeHttpRequests {
                it.requestMatchers(
                    "/", "/oauth2/**", "/api/public/**",
                    "/swagger-ui/**", "/api-docs/**", "/api/v1/**"
                )
                    .permitAll().anyRequest().authenticated()
            }
            .formLogin { it.disable() }
            .oauth2Login {
                it.userInfoEndpoint { oAuth2Config ->
                    oAuth2Config.userService(oAuth2UserService)
                }
                    .successHandler(oAuth2SuccessHandler)
                    .failureHandler(oAuth2FailureHandler)
            }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
            }
        return http.build()
    }
}

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

        if (principal.isNew) {
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

@Component
class OAuth2FailureHandler(
) : SimpleUrlAuthenticationFailureHandler() {
    override fun onAuthenticationFailure(
        request: HttpServletRequest, response: HttpServletResponse, exception: AuthenticationException?
    ) {
        response.status = HttpStatus.UNAUTHORIZED.value()
    }
}