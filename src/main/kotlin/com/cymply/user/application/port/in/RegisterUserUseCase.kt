package com.cymply.user.application.port.`in`

interface RegisterUserUseCase {
    fun registerOAuth2User(command: RegisterOAuth2UserCommand): Long
}