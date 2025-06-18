package com.cymply.auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfigurer(
    private val oauth2LoginConfigurer: (HttpSecurity) -> Unit,
    private val oauth2ResourceServerConfigurer: (HttpSecurity) -> Unit
) {
    companion object {
        val WHITELIST: Array<String> = arrayOf(
            "/", "/h2-console/**", "/oauth2/**", "/login/**", "/public/**",
            "/swagger-ui/**", "/api-docs/**", "/api/v1/auth/**"
        )
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        /**
         * 보안 및 인증 방식 설정
         */
        http.csrf { it.disable() }
            .headers { it.frameOptions { frame -> frame.sameOrigin() } }
            .cors { CorsConfig().corsConfigurationSource() }
            .formLogin { it.disable() }

        http.authorizeHttpRequests { authz ->
            authz.requestMatchers(*WHITELIST).permitAll()   // 전체 허용
                .requestMatchers(   // 비회원 권한
                    "/api/v1/users/signup/**",
                    "/api/v1/users/check/nickname/**"
                ).hasAuthority("SCOPE_user:signup")
                .requestMatchers("/api/v1/users/**").hasAuthority("SCOPE_user")
                .requestMatchers("/api/v1/playlists/**").hasAuthority("SCOPE_playlist")
                .anyRequest().authenticated()
        }

        oauth2LoginConfigurer.invoke(http)
        oauth2ResourceServerConfigurer.invoke(http)

        return http.build()
    }
}
