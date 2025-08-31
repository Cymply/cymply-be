package com.cymply.user.adapter.out.persistence.entity

import com.cymply.common.model.Gender
import com.cymply.user.domain.UserProfile
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated

@Embeddable
data class UserEntityProfile(
    @Enumerated(EnumType.STRING)
    val gender: Gender?,

    @Enumerated(EnumType.STRING)
    val ageRange: UserProfile.AgeRange?
)