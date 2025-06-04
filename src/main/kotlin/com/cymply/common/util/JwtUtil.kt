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
class JwtUtil(
    @Value("\${spring.jwt.secret}") private val secret: String
) {
    companion object {
        const val USERNAME_KEY = "username"
        const val ROLE_KEY = "role"
    }

    private lateinit var key: SecretKey

    @PostConstruct
    fun init() {
        key = SecretKeySpec(
            secret.toByteArray(StandardCharsets.UTF_8),
            Jwts.SIG.HS256.key().build().algorithm
        )
    }

    fun getUsername(token: String): String {
        return Jwts.parser().verifyWith(key).build()
            .parseSignedClaims(token)
            .payload
            .get(USERNAME_KEY, String::class.java)
    }

    fun getRole(token: String): String {
        return Jwts.parser().verifyWith(key).build()
            .parseSignedClaims(token)
            .payload
            .get(ROLE_KEY, String::class.java)
    }

    fun isExpired(token: String): Boolean {
        return Jwts.parser().verifyWith(key).build()
            .parseSignedClaims(token)
            .payload
            .expiration
            .before(Date())
    }

    fun generate(username: String, role: String, expired: Long = 1000): String {
        return Jwts.builder()
            .claim(USERNAME_KEY, username)
            .claim(ROLE_KEY, role)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + expired))
            .signWith(key)
            .compact()
    }

}