package com.cymply.user.adapter.`in`.auth

import com.cymply.auth.application.port.out.RegisterUserPort
import com.cymply.user.application.port.`in`.JoinUserCommand
import com.cymply.user.application.port.`in`.JoinUserUseCase
import org.springframework.stereotype.Component

@Component
class RegisterUserAdapter(
    private val joinUserUseCase: JoinUserUseCase
) : RegisterUserPort {
    override fun registerUser(request: RegisterUserRequest) {
        val command = JoinUserCommand.of(request)
        joinUserUseCase.joinUser(command)
    }
}
