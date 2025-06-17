package com.cymply.auth.application.port.out

interface LoadTokenPort {
    fun loadToken(key: String): String?
}