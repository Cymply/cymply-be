package com.cymply.user.application.port.out

import com.cymply.user.domain.User

interface SaveUserPort {
    fun saveUser(user: User): Long
}