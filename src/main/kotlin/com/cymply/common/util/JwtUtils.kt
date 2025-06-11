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
    private val secret: String,

    @Value("\${spring.jwt.keys.identities}")
    private val identitiesKey: String,

    @Value("\${spring.jwt.keys.iss}")
    private val issKey: String,
) {
    private lateinit var key: SecretKey

    @PostConstruct
    fun init() {
        key = SecretKeySpec(
            secret.toByteArray(StandardCharsets.UTF_8),
            Jwts.SIG.HS256.key().build().algorithm
        )
    }

    fun getIdentities(token: String): Map<String, Any?> {
        return Jwts
            .parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
            .get(identitiesKey, Map::class.java) as Map<String, Any?>
    }

    fun isExpired(token: String): Boolean {
        return Jwts.parser().verifyWith(key).build()
            .parseSignedClaims(token)
            .payload
            .expiration
            .before(Date())
    }

    fun generate(identity: Map<String, Any?>, expired: Long = 1000): String {
        return Jwts.builder()
            .issuer(issKey)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expired))
            .signWith(key)
            .claims(mapOf(identitiesKey to identity))
            .compact()
    }
}