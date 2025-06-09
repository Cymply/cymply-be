package com.cymply.auth.adapter.`in`.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class PrincipalDetail(
    val id: Long? = null,
    private val attributes: Map<String, Any?>
) : OAuth2User {
    override fun getName(): String {
        return id?.toString() ?: "unknown"
    }

    override fun getAttributes(): Map<String, Any?> {
        return attributes
    }

    override fun getAuthorities(): MutableCollection<GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority("USER"))
    }

    val isNew: Boolean
        get() = id == null
}