package com.cymply.user.domain

import java.time.LocalDateTime

abstract class User(
    val id: Long? = null,
    val role: Role,
    val email: String,
    val nickname: String,
    val profile: UserProfile,
    val deletedAt: LocalDateTime? = null
) {
    enum class Role {
        USER,
        ADMIN
    }

    fun getIdOrThrow() = id ?: throw IllegalStateException("User id is null.")

    fun verifyValidUser() {
        if (!isActiveUser()) {
            throw IllegalArgumentException("Already withdraw, User: $id")
        }
    }

    fun isActiveUser() = deletedAt == null
}
