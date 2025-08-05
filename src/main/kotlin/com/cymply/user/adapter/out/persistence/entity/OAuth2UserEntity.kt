package com.cymply.user.adapter.out.persistence.entity

import com.cymply.user.domain.User
import com.cymply.user.domain.UserProvider
import jakarta.persistence.*

@Entity
@DiscriminatorValue("OAUTH2")
class OAuth2UserEntity(
    id: Long? = null,
    role: User.Role,
    email: String,
    nickname: String,
    profile: UserEntityProfile?,

    @Column(unique = true)
    val sub: String,

    @Enumerated(EnumType.STRING)
    val provider: UserProvider
) : UserEntity(id, role, email, nickname, profile)

