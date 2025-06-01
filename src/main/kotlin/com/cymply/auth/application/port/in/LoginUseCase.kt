package com.cymply.auth.application.port.`in`

import com.cymply.auth.application.service.OAuth2LoginCommand

interface LoginUseCase {
    fun oAuth2Login(command: OAuth2LoginCommand)
}