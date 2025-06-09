package com.cymply.common.util

import io.jsonwebtoken.Jwts
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Component
class JwtUtils(
    @Value("\${spring.jwt.secret}")
    private val secret: String
) {
    private lateinit var key: SecretKey

    companion object {
        const val IDENTITY_KEY = "identity"
        const val ISS_KEY = "api.cymply.com"
        const val ACCESS_TOKEN_EXPIRATION_IN_SECONDS = (24 * 60 * 60 * 1000).toLong()  // 1 day
        const val REFRESH_TOKEN_EXPIRATION_IN_SECONDS = (7 * 24 * 60 * 60 * 1000).toLong()  // 7 day
        const val TEMPORARY_TOKEN_EXPIRATION_IN_SECONDS = (10 * 60 * 1000).toLong() // 10 min
    }

    @PostConstruct
    fun init() {
        key = SecretKeySpec(
            secret.toByteArray(StandardCharsets.UTF_8),
            Jwts.SIG.HS256.key().build().algorithm
        )
    }

    fun getIdentity(token: String): Map<String, Any?> {
        return Jwts
            .parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
            .get(IDENTITY_KEY, Map::class.java) as Map<String, Any?>
    }

    fun isExpired(token: String): Boolean {
        return Jwts.parser().verifyWith(key).build()
            .parseSignedClaims(token)
            .payload
            .expiration
            .before(Date())
    }

    fun generate(identity: Map<String, Any?>, expired: Long = 1000): String {
        val claims = mapOf(
            IDENTITY_KEY to identity
        )

        return Jwts.builder()
            .issuer(ISS_KEY)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expired))
            .signWith(key)
            .claims(claims)
            .compact()
    }
}