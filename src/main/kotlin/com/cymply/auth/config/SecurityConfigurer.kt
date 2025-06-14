package com.cymply.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfigurer(
    private val oauth2LoginConfigurer: (HttpSecurity) -> Unit,
    private val oauth2ResourceServerConfigurer: (HttpSecurity) -> Unit
) {
    companion object {
        val WHITELIST: Array<String> = arrayOf(
            "/", "/oauth2/**", "/login/**", "/api/public/**",
            "/swagger-ui/**", "/api-docs/**", "/api/v1/**"
        )
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        /**
         * 보안 및 인증 방식 설정
         */
        http
            .csrf { it.disable() }
            .cors { CorsConfig().corsConfigurationSource() }
            .formLogin { it.disable() }

        http.authorizeHttpRequests {
            it.requestMatchers(*WHITELIST).permitAll()
                .anyRequest().authenticated()
        }

        oauth2LoginConfigurer.invoke(http)
        oauth2ResourceServerConfigurer.invoke(http)

        return http.build()
    }

}
