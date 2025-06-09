package com.cymply.user.application.service

import com.cymply.user.application.port.`in`.RegisterUserUseCase
import com.cymply.user.application.port.out.SaveUserPort
import com.cymply.user.domain.OAuth2User
import com.cymply.user.domain.UserProfile
import com.cymply.user.domain.UserProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterUserService(
    private val saveUserPort: SaveUserPort
) : RegisterUserUseCase {
    @Transactional
    override fun registerOAuth2User(command: RegisterOAuth2UserCommand): Long {
        val user = OAuth2User.of(
            sub = command.sub,
            provider = UserProvider.valueOf(command.provider.uppercase()),
            email = command.email,
            nickname = command.nickname,
            profile = UserProfile(command.name, command.gender, command.birth)
        )
        return saveUserPort.saveUser(user)
    }
}