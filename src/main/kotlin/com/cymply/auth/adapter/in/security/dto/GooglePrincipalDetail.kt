package com.cymply.auth.adapter.`in`.security.dto

import org.springframework.security.oauth2.core.user.OAuth2User

class GooglePrincipalDetail(
    id: Long,
    email: String,
    role: String,
    sub: String,
    provider: String,
    private val name: String,
) : PrincipalDetail(id, email, role, sub, provider) {
    companion object {
        fun from(id: Long, role: String, oAuth2User: OAuth2User): GooglePrincipalDetail {
            val attributes = oAuth2User.attributes
            val sub = attributes["sub"] as String
            val email = attributes["email"] as String
            val name = attributes["name"] as String
            return GooglePrincipalDetail(
                id, email, role, sub, "GOOGLE", name
            )
        }
    }

    override fun getAttributes(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "email" to email,
            "role" to role,
            "sub" to sub,
            "name" to name,
            "provider" to provider
        )
    }
}