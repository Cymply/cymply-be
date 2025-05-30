package com.cymply.auth.application.service

import com.cymply.auth.application.port.`in`.LoginUseCase
import com.cymply.user.application.port.`in`.GetUserUseCase
import com.cymply.user.application.port.`in`.RegisterUserUseCase
import com.cymply.user.application.port.`in`.RegisterOAuth2UserCommand
import org.springframework.stereotype.Service

@Service
class LoginService(
    private val getUserUseCase: GetUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase
) : LoginUseCase {
    /**
     * 회원 조회 및 검증
     * 1. 외부 인증 서비스에서 받은 정보를 통해 검증
     * 2. 일치하는 회원이 없는 경우 회원 정보를 저장
     */
    override fun oAuth2Login(command: OAuth2LoginCommand) {
        val user = getUserUseCase.getActiveUser(command.provider, command.id)
        if (user == null) {
            val registerOAuth2UserCommand = RegisterOAuth2UserCommand(
                sub = command.id,
                provider = command.provider,
                email = command.email,
                name = command.name,
                birth = command.birth
            )
            registerUserUseCase.registerOAuth2User(registerOAuth2UserCommand)
        }
    }
}