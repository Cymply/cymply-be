package com.cymply.auth.application.port.`in`

import com.cymply.auth.application.service.AuthenticationToken

interface ReissueTokenUseCase {
    fun reissueToken(command: ReissueTokenCommand): AuthenticationToken
}