package com.cymply.auth.adapter.`in`.security.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

abstract class PrincipalDetail(
    val id: Long = -1,
    val email: String,
    val role: String,
    val sub: String,
    val provider: String
) : OAuth2User {
    override fun getName() = id.toString()

    override fun getAttributes(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "sub" to sub,
            "email" to email,
            "provider" to provider
        )
    }

    override fun getAuthorities(): MutableCollection<GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role))
    }
}