package com.cymply.user.domain

import java.time.LocalDateTime

class RecipientCode(
    val id: Long? = null,
    val code: String,
    val recipientId: Long,
    val createdAt: LocalDateTime,
    val expiredAt: LocalDateTime? = null,
) {
    companion object {
        fun of(code: String, recipientId: Long): RecipientCode {
            return RecipientCode(
                code = code,
                recipientId = recipientId,
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