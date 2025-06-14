package com.cymply.user.domain

import java.time.LocalDateTime

class OAuth2User(
    id: Long? = null,
    email: String,
    nickname: String,
    role: Role,
    profile: UserProfile?,
    deletedAt: LocalDateTime? = null,
    /**
     * 소셜 플랫폼 종류
     */
    val provider: UserProvider,
    /**
     * 소셜 플랫폼 식별자
     */
    val sub: String
) : User(id, email, nickname, role, profile, deletedAt) {
    companion object {
        fun of(
            email: String, nickname: String, role: Role,
            profile: UserProfile, provider: UserProvider, sub: String
        ) = OAuth2User(
            email = email, nickname = nickname, role = role,
            profile = profile, provider = provider, sub = sub
        )
    }
}

