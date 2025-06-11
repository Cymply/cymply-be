package com.cymply.auth.adapter.`in`.security

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

data class PrincipalDetail(
    private val attributes: Map<String, Any?>
) : OAuth2User, UserDetails {
    override fun getName(): String {
        val nickname = attributes["nickname"]
        if (nickname != null) {
            return nickname.toString()
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

    override fun getPassword(): String? {
        return null
    }

    override fun getUsername(): String {
        val id = attributes["id"]
        return id?.toString() ?: "-1"
    }
}