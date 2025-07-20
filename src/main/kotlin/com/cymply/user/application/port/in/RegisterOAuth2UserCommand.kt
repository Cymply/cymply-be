package com.cymply.user.application.port.`in`

import com.cymply.common.model.Gender

data class RegisterOAuth2UserCommand(
    val sub: String,
    val provider: String,
    val email: String,
    val nickname: String,
    val gender: Gender? = null,
    val ageRange: String? = null
)