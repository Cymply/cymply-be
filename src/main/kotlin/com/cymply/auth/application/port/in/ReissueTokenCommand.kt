package com.cymply.auth.application.port.`in`

data class ReissueTokenCommand(
    val sub: String,
    val provider: String
)