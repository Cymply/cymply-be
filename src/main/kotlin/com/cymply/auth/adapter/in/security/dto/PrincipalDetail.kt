package com.cymply.auth.adapter.`in`.security.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

class PrincipalDetail(
    private val attributes: Map<String, Any?>
) : OAuth2User {
    override fun getName(): String {
        val id = attributes["id"]
        if (id != null) {
            return id.toString()
        }
        return "Unknown"
    }

    override fun getAttributes(): Map<String, Any?> {
        return attributes
    }

    override fun getAuthorities(): MutableCollection<GrantedAuthority> {
        val role = attributes["role"]
        if (role != null) {
            return mutableListOf(SimpleGrantedAuthority("ROLE_$role"))
        }
        return mutableListOf(SimpleGrantedAuthority("ROLE_TEMPORARY"))
    }
}