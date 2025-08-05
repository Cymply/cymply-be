package com.cymply.user.adapter.out.persistence.entity

import com.cymply.user.domain.User
import jakarta.persistence.*

@Entity
@DiscriminatorValue("NORMAL")
class NormalUserEntity(
    id: Long? = null,
    role: User.Role,
    email: String,
    nickname: String,
    profile: UserEntityProfile?,
    val password: String
) : UserEntity(id, role, email, nickname, profile)

