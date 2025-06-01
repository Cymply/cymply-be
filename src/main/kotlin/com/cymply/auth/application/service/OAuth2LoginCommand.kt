package com.cymply.auth.application.service

data class OAuth2LoginCommand(
    val id: String,
    val provider: String,
    val email: String,
    val name: String? = null,
    val birth: String? = null
)