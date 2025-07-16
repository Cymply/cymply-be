package com.cymply.letter.domain

import java.time.LocalDateTime

class LetterNickname(
    val senderId: Long,
    val recipientId: Long,
    val nickname: String,
    val createdAt: LocalDateTime,
) {
    companion object {
        fun of(senderId: Long, recipientId: Long, nickname: String) =
            LetterNickname(senderId, recipientId, nickname, LocalDateTime.now())
    }
}