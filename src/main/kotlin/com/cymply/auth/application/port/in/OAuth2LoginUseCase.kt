package com.cymply.auth.application.port.`in`

import com.cymply.auth.application.service.OAuth2LoginResult

interface OAuth2LoginUseCase {
    fun login(command: OAuth2LoginCommand): OAuth2LoginResult
}