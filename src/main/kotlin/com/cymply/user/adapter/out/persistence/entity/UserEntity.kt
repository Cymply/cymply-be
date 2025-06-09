package com.cymply.user.adapter.out.persistence.entity

import com.cymply.common.model.BaseTimeEntity
import com.cymply.common.model.Gender
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "users")
@DiscriminatorColumn(name = "user_type")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class UserEntity(
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(unique = true)
    val nickname: String,

    val name: String? = null,

    @Enumerated(EnumType.STRING)
    val gender: Gender? = null,

    val birth: LocalDate? = null
) : BaseTimeEntity()

