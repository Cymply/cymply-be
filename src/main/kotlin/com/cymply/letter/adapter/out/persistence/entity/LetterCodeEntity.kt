package com.cymply.letter.adapter.out.persistence.entity

import com.cymply.common.model.BaseTimeEntity
import jakarta.persistence.*
import jakarta.persistence.GenerationType.*
import java.time.LocalDateTime

@Entity
@Table(name = "letter_code")
class LetterCodeEntity(
    @Id
    @Column(name = "letter_code_id")
    @GeneratedValue(strategy = IDENTITY)
    val id: Long? = null,
    @Column(unique = true)
    val code: String,
    @Column(unique = true)
    val recipientId: Long,
    val expiredAt: LocalDateTime? = null
) : BaseTimeEntity() {

}