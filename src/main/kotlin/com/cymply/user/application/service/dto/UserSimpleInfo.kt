package com.cymply.user.application.service.dto

import com.cymply.user.domain.User

data class UserSimpleInfo(
    val id: Long,
    val email: String,
    val nickname: String,
    val role: User.Role
) {
    companion object {
        fun from(user: User) = UserSimpleInfo(
            id = user.getIdOrThrow(),
            email = user.email,
            nickname = user.nickname,
            role = user.role
        )
    }
}