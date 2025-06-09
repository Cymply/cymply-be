package com.cymply.user.application.service

import com.cymply.user.domain.User

data class UserSimpleInfo(
    val id: Long,
    val email: String,
    val nickname: String?,
    val name: String?
) {
    companion object {
        fun from(user: User) = UserSimpleInfo(
            id = user.getIdOrThrow(),
            email = user.email,
            nickname = user.nickname,
            name = user.profile.name
        )
    }
}