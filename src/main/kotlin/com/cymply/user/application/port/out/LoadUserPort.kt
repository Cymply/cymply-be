package com.cymply.user.application.port.out

import com.cymply.user.domain.User
import com.cymply.user.domain.UserProvider

interface LoadUserPort {
    fun loadUserByEmail(email: String): User?

    fun loadUserBySubAndProvider(sub: String, provider: UserProvider): User?
}