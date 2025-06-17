package com.cymply.auth.application.port.out

interface SaveTokenPort {
    fun saveRefreshToken(token: String)
}