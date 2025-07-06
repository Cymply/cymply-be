package com.cymply.letter.adapter.out.persistence.entity

import com.cymply.common.model.BaseTimeEntity
import jakarta.persistence.*
import jakarta.persistence.GenerationType.*
import java.time.LocalDateTime

@Entity
@Table(name = "letter")
class LetterEntity(
    @Id
    @Column(name = "letter_id")
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,
    @Lob
    val content: String,
    val recipientId: Long,
    val senderId: Long,
    val musicId: Long,
    val readAt: LocalDateTime
) : BaseTimeEntity()