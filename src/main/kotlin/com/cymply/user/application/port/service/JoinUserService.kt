package com.cymply.user.application.port.service

import com.cymply.user.domain.User
import com.cymply.user.application.port.`in`.JoinUserCommand
import com.cymply.user.application.port.`in`.JoinUserUseCase
import com.cymply.user.application.port.out.SaveUserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class JoinUserService(
    private val saveUserPort: SaveUserPort
) : JoinUserUseCase {
    @Transactional
    override fun joinUser(command: JoinUserCommand): Long {
        val user = User.join(
            email = command.email,
            provider = command.provider,
            sub = command.sub,
            name = command.name,
            nickname = command.nickname
        )
        return saveUserPort.saveUser(user)
    }
}