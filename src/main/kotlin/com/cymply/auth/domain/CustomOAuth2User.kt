package com.cymply.auth.domain

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class CustomOAuth2User(
    val id: Long,
    val email: String,
    val provider: String,
    val username: String?,
    val birth: String?
) : OAuth2User {
    override fun getName(): String {
        return email
    }

    override fun getAttributes(): MutableMap<String, Any?> {
        return mutableMapOf(
            ("id" to id),
            ("email" to email),
            ("provider" to provider),
            ("name" to name),
            ("birth" to birth)
        )
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
    }
}