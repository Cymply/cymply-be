package com.cymply.user.adapter.out.persistence

import com.cymply.common.model.BaseTimeEntity
import com.cymply.user.domain.UserProvider
import jakarta.persistence.*

@Entity
class UserEntity(
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @Column(nullable = false, unique = true)
    val email: String,
    val name: String? = null,
    val nickname: String? = null,
    val thumbnail: String? = null,
    @Enumerated(EnumType.STRING)
    val provider: UserProvider? = null,
    @Column(unique = true)
    val sub: String? = null
) : BaseTimeEntity()