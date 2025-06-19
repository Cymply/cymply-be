package com.cymply.auth.config

import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import java.nio.charset.StandardCharsets
import javax.crypto.spec.SecretKeySpec

/**
 * OAuth 2.0 Resource Server JWT
 * JwtAuthenticationToken will be set on the SecurityContextHolder by the authentication Filter.
 */
@Configuration
class OAuth2ResourceServerConfigurer {

    @Value("\${spring.security.jwt.secret}")
    private lateinit var key: String

    @Bean
    fun oauth2ResourceServerConfigurer(): (HttpSecurity) -> Unit = { http ->
        http.oauth2ResourceServer { resourceServer ->
            resourceServer.jwt {
                it.jwtAuthenticationConverter(jwtAuthenticationConverter())
            }
        }
    }

    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val grantedAuthoritiesConverter = JwtGrantedAuthoritiesConverter()
        grantedAuthoritiesConverter.setAuthoritiesClaimName("scope")

        /**
         * JwtAuthenticationConverter
         * Jwt를 부여된 권한 Collection으로 변환
         */
        val authenticationConverter = JwtAuthenticationConverter()
        authenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter)
        return authenticationConverter
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        val secretKey = SecretKeySpec(
            key.toByteArray(StandardCharsets.UTF_8),
            Jwts.SIG.HS256.key().build().algorithm
        )
        return NimbusJwtDecoder.withSecretKey(secretKey).build()
    }
}
