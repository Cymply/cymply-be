package com.cymply.user.application.service

import com.cymply.user.domain.User
import com.cymply.user.application.port.`in`.RegisterUserUseCase
import com.cymply.user.application.port.`in`.RegisterOAuth2UserCommand
import com.cymply.user.application.port.out.SaveUserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterUserService(
    private val saveUserPort: SaveUserPort
) : RegisterUserUseCase {
    @Transactional
    override fun registerOAuth2User(command: RegisterOAuth2UserCommand): Long {
        val user = User.of(
            sub = command.sub,
            provider = command.provider,
            email = command.email,
            name = command.name,
            birth = command.birth
        )
        return saveUserPort.saveUser(user)
    }
}