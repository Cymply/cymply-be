package com.cymply.auth.application.port.`in`

import com.cymply.auth.application.service.AuthenticationToken

interface OAuth2LoginUseCase {
    fun oAuth2Login(command: OAuth2LoginCommand): AuthenticationToken
}