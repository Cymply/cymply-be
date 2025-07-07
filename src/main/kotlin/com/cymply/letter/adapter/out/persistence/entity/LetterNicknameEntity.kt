package com.cymply.letter.adapter.out.persistence.entity

import com.cymply.common.model.BaseTimeEntity
import jakarta.persistence.*
import jakarta.persistence.GenerationType.*

@Entity
@Table(name = "letter_nickname")
class LetterNicknameEntity(
    @Id
    @Column(name = "letter_nickname_id")
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,
    val senderId: Long,
    val recipientId: Long,
    val nickname: String,
) : BaseTimeEntity()