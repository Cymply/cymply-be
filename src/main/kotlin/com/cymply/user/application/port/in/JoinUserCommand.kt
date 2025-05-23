package com.cymply.user.application.port.`in`

import com.cymply.user.adapter.`in`.auth.RegisterUserRequest

data class JoinUserCommand(
    val email: String,
    val name: String? = null,
    val nickname: String? = null,
    val provider: String? = null,
    val sub: String? = null
) {
    companion object {
        fun of(request: RegisterUserRequest) =
            JoinUserCommand(
                email = request.email,
                name = request.name,
                nickname = request.nickname,
                provider = request.provider,
                sub = request.sub
            )
    }
}