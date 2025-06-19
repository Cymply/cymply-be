package com.cymply.auth.config

import com.cymply.auth.adapter.`in`.security.OAuth2FailureHandler
import com.cymply.auth.adapter.`in`.security.OAuth2LoginSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity

/**
 * OAuth2 Client 설정
 * https://docs.spring.io/spring-security/reference/servlet/oauth2/client/index.html
 */
@Configuration
class OAuth2ClientConfigurer(
    private val oAuth2LoginSuccessHandler: OAuth2LoginSuccessHandler
) {
    @Bean
    fun oauth2LoginConfigurer(): (HttpSecurity) -> Unit = { http ->
        http.oauth2Login {
            it.userInfoEndpoint {}
                .successHandler(oAuth2LoginSuccessHandler)
                .failureHandler(OAuth2FailureHandler())
        }
    }
}
