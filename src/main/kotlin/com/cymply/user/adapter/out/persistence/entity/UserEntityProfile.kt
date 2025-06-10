package com.cymply.user.adapter.out.persistence.entity

import com.cymply.common.model.Gender
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.time.LocalDate

@Embeddable
data class UserEntityProfile(
    val name: String? = null,

    @Enumerated(EnumType.STRING)
    val gender: Gender? = null,

    val birth: LocalDate? = null
)