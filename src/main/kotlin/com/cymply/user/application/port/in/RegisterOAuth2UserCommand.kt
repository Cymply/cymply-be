package com.cymply.user.application.port.`in`

import com.cymply.common.model.Gender
import java.time.LocalDate

data class RegisterOAuth2UserCommand(
    val sub: String,
    val provider: String,
    val email: String,
    val nickname: String,
    val name: String? = null,
    val gender: Gender? = null,
    val birth: LocalDate? = null
)