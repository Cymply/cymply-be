package com.cymply.user.adapter.out.persistence.entity

import com.cymply.common.model.BaseTimeEntity
import com.cymply.user.domain.User
import jakarta.persistence.*

@Entity
@Table(name = "users")
@DiscriminatorColumn(name = "user_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class UserEntity(
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Enumerated(EnumType.STRING)
    val role: User.Role,

    @Column(nullable = false)
    val email: String,

    @Column(unique = true)
    val nickname: String,

    /**
     * lazy loading
     */
    @Embedded
    val profile: UserEntityProfile?
) : BaseTimeEntity()

