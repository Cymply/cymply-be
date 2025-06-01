package com.cymply.user.application.port.`in`

import com.cymply.user.domain.User
import com.cymply.user.domain.UserProvider

data class UserInfo(
    val id: Long,
    val email: String,
    val provider: UserProvider?,
    val name: String?
) {
    companion object {
        fun from(user: User) = UserInfo(
            id = user.getIdOrThrow(),
            provider = user.provider,
            email = user.email,
            name = user.name
        )
    }
}