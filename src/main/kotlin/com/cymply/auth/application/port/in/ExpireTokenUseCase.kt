package com.cymply.auth.application.port.`in`


interface ExpireTokenUseCase {
    fun expireToken(refreshToken: String)
}