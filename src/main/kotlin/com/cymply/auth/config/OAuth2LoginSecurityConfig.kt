package com.cymply.auth.config

import com.cymply.auth.adapter.`in`.security.CustomOAuth2UserService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler

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
                it.requestMatchers("/", "/oauth2/**", "/api/public/**")
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
) : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        response.status = HttpStatus.OK.value()
        response.sendRedirect("http://localhost:3000/")
    }
}

@Component
class OAuth2FailureHandler(
) : AuthenticationFailureHandler {
    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: AuthenticationException?
    ) {
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.sendRedirect("http://localhost:3000/login/fail")
    }
}