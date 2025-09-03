package com.cymply.user.application.service

import com.cymply.user.application.port.`in`.RegisterUserUseCase
import com.cymply.user.application.port.out.LoadUserPort
import com.cymply.user.application.port.out.SaveUserPort
import com.cymply.user.application.port.`in`.RegisterOAuth2UserCommand
import com.cymply.user.domain.OAuth2User
import com.cymply.user.domain.User
import com.cymply.user.domain.UserProfile
import com.cymply.user.domain.UserProvider
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterUserService(
    private val loadUserPort: LoadUserPort,
    private val saveUserPort: SaveUserPort
) : RegisterUserUseCase {
    @Transactional
    override fun registerOAuth2User(command: RegisterOAuth2UserCommand): Long {
        val provider = UserProvider.valueOf(command.provider.uppercase())

        val exist = loadUserPort.loadUserBySubAndProvider(command.sub, provider)
            .find { !it.isDeletedUser() }

        if (exist != null) {
            return 0
        }

        val profile = UserProfile(
            command.gender,
            command.ageRange?.let { UserProfile.AgeRange.from(it) }
        )
        val user = OAuth2User.of(
            sub = command.sub,
            provider = provider,
            role = User.Role.USER,
            email = command.email,
            nickname = command.nickname,
            profile = profile
        )
        return saveUserPort.saveUser(user)
    }
}