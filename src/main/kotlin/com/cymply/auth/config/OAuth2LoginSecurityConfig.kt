package com.cymply.auth.config

import com.cymply.auth.adapter.`in`.security.CustomAuthenticationEntryPoint
import com.cymply.auth.adapter.`in`.security.CustomOAuth2UserService
import com.cymply.auth.adapter.`in`.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class OAuth2LoginSecurityConfig(
    private val oAuth2UserService: CustomOAuth2UserService,
    private val oAuth2SuccessHandler: OAuth2SuccessHandler,
    private val oAuth2FailureHandler: OAuth2FailureHandler,
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val customAuthenticationEntryPoint: CustomAuthenticationEntryPoint
) {
    companion object {
        val WHITELIST: Array<String> = arrayOf(
            "/", "/oauth2/**", "/login/**", "/api/public/**",
            "/swagger-ui/**", "/api-docs/**", "/api/v1/**"
        )
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .cors { CorsConfig().corsConfigurationSource() }
            .formLogin { it.disable() }
            .sessionManagement { it.disable() }

        http.authorizeHttpRequests {
            it.requestMatchers(*WHITELIST).permitAll()
                .anyRequest().authenticated()
        }

        /**
         * 토큰 인증 필터 설정
         */
        http
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling { it.authenticationEntryPoint(customAuthenticationEntryPoint) }

        /**
         * oAuth2 인증 설정
         */
        http.oauth2Login { config ->
            config.userInfoEndpoint { it.userService(oAuth2UserService) }
                .successHandler(oAuth2SuccessHandler)
                .failureHandler(oAuth2FailureHandler)
        }

        return http.build()
    }
}
