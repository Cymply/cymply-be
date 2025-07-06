package com.cymply.letter.domain

import java.time.LocalDateTime

class Letter(
    val id: Long? = null,
    val content: String,
    val musicId: Long,
    val recipientId: Long,  // 수신자 아이디
    val senderId: Long, // 발신자 아이디
    var readAt: LocalDateTime? = null,
    val createdAt: LocalDateTime,
    var deletedAt: LocalDateTime? = null,
) {
    companion object {
        fun of(content: String, musicId: Long, recipientId: Long, senderId: Long): Letter {
            return Letter(
                content = content,
                musicId = musicId,
                recipientId = recipientId,
                senderId = senderId,
                createdAt = LocalDateTime.now()
            )
        }
    }

    fun getIdOrThrow(): Long {
        if (id == null) {
            throw IllegalStateException("id is null")
        }
        return id
    }

    fun delete() {
        deletedAt = LocalDateTime.now()
    }

    fun isRead(): Boolean {
        return readAt != null
    }
}