package com.cymply.letter.application.dto

import java.time.LocalDateTime

data class LetterSummary(
    val senderId: Long,
    val senderName: String,
    val letterId: Long,
    val musicTitle: String,
    val musicArtist: String,
    val thumbnailUrl: String,
    val videoUrl: String,
    val title: String,
    val content: String,
    val sentAt: LocalDateTime,
    val readAt: LocalDateTime? = null,
)