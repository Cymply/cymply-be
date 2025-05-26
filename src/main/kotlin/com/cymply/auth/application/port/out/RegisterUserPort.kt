package com.cymply.auth.application.port.out

import com.cymply.user.adapter.`in`.auth.RegisterUserRequest

interface RegisterUserPort {
    fun registerUser(request: RegisterUserRequest)
}