package com.cymply.user.application.port.out

import com.cymply.user.domain.User
import com.cymply.user.domain.UserProvider

interface LoadUserPort {
    fun loadUserById(id: Long): User

    fun loadUserBySubAndProvider(sub: String, provider: UserProvider): User?

    fun loadUserByNickname(nickname: String): User?
}