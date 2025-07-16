package com.cymply.user.adapter.out.persistence.entity

import com.cymply.common.model.BaseTimeEntity
import jakarta.persistence.*
import jakarta.persistence.GenerationType.*
import java.time.LocalDateTime

@Entity
@Table(name = "recipient_code")
class RecipientCodeEntity(
    @Id
    @Column(name = "recipient_code_id")
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,

    @Column(unique = true)
    val code: String,

    @Column(unique = true)
    val recipientId: Long,

    val expiredAt: LocalDateTime? = null
) : BaseTimeEntity()