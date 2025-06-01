package com.cymply.user.application.port.`in`

data class RegisterOAuth2UserCommand(
    val sub: String,
    val provider: String,
    val email: String,
    val name: String? = null,
    val birth: String? = null
)