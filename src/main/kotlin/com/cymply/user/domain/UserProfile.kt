package com.cymply.user.domain

import com.cymply.common.model.Gender
import java.time.LocalDate

data class UserProfile(
    val name: String? = null,
    val gender: Gender? = null,
    val birth: LocalDate? = null,
) 