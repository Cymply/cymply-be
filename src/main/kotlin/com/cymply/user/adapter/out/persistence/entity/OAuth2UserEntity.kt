package com.cymply.user.adapter.out.persistence.entity

import com.cymply.common.model.Gender
import com.cymply.user.domain.UserProvider
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@DiscriminatorValue("OAUTH2_USER")
class OAuth2UserEntity(
    id: Long? = null,

    email: String,

    nickname: String,

    name: String?,

    gender: Gender,

    birth: LocalDate,

    @Column(unique = true)
    val sub: String? = null,

    @Enumerated(EnumType.STRING)
    val provider: UserProvider? = null

) : UserEntity(id, email, nickname, name, gender, birth)

