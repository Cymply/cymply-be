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
    @Value("\${spring.security.jwt.secret}")
    private val secret: String,
    @Value("\${spring.app.host}")
    private val issuer: String,
) {
    private lateinit var key: SecretKey

    @PostConstruct
    fun init() {
        key = SecretKeySpec(
            secret.toByteArray(StandardCharsets.UTF_8),
            Jwts.SIG.HS256.key().build().algorithm
        )
    }

    fun isExpired(token: String): Boolean {
        return Jwts.parser().verifyWith(key).build()
            .parseSignedClaims(token)
            .payload
            .expiration
            .before(Date())
    }

    fun getId(token: String): String {
        return Jwts.parser().verifyWith(key).build()
            .parseSignedClaims(token)
            .payload
            .id
    }

    fun extractId(token: String): Long {
        return (Jwts.parser().verifyWith(key).build()
            .parseSignedClaims(token)
            .payload["id"] as Int)
            .toLong()
    }

    fun generate(identities: Map<String, Any?>, expired: Long): String {
        return Jwts.builder()
            .issuer(issuer)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expired))
            .signWith(key)
            .id(UUID.randomUUID().toString())
            .claims(identities)
            .compact()
            .toString()
    }

}