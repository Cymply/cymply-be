package com.cymply.user.application.port.`in`

import com.cymply.user.application.service.RegisterOAuth2UserCommand

interface RegisterUserUseCase {
    fun registerOAuth2User(command: RegisterOAuth2UserCommand): Long
}