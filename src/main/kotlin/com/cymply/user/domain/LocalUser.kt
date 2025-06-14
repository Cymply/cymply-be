package com.cymply.user.domain

import java.time.LocalDateTime

class LocalUser(
    id: Long? = null,
    email: String,
    nickname: String,
    role: User.Role,
    profile: UserProfile?,
    deletedAt: LocalDateTime? = null,
    val password: String
) : User(id, email, nickname, role, profile, deletedAt) {
    companion object {
        fun of(
            role: Role,
            email: String,
            nickname: String,
            profile: UserProfile,
            password: String
        ) = LocalUser(
            role = role,
            email = email,
            nickname = nickname,
            profile = profile,
            password = password
        )
    }
}

