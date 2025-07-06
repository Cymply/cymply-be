package com.cymply.letter.domain

import java.time.LocalDateTime

class LetterCode(
    val id: Long? = null,
    val recipientId: Long,
    val code: String,
    val createdAt: LocalDateTime,
    val expiredAt: LocalDateTime? = null,
) {
    companion object {
        fun of(recipientId: Long, code: String): LetterCode {
            return LetterCode(
                recipientId = recipientId,
                code = code,
                createdAt = LocalDateTime.now()
            )
        }
    }

    fun verifyIsNotExpired() {
        if (expiredAt != null && expiredAt.isBefore(LocalDateTime.now())) {
            throw IllegalStateException("Expired at $expiredAt")
        }
    }
}