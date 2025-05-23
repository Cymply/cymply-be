package com.cymply.user.application.port.out

import com.cymply.user.domain.User

interface LoadUserPort {
    fun loadUserByEmail(email: String): User?
}