package com.cymply.user.domain

import java.time.LocalDateTime

abstract class User(
    val id: Long? = null,
    val email: String,
    val nickname: String,
    val role: Role,
    val profile: UserProfile?,
    var deletedAt: LocalDateTime? = null
) {
    enum class Role {
        USER,
        ADMIN
    }

    fun getIdOrThrow() = id ?: throw IllegalStateException("User id is null.")

    fun verifyValidUser() {
        if (isDeletedUser()) {
            throw IllegalArgumentException("Already withdraw, User: $id")
        }
    }

    fun withdraw() {
        verifyValidUser();
        deletedAt = LocalDateTime.now()
    }

    fun isDeletedUser() = deletedAt != null
}
