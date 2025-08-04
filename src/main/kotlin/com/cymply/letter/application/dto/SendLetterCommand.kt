package com.cymply.letter.application.dto

data class SendLetterCommand(
    val senderId: Long,
    val recipientCode: String,
    val title: String,
    val content: String,
    val musicTitle: String,
    val musicArtist: String,
) {
    companion object {
        fun of(
            senderId: Long,
            recipientCode: String,
            title: String,
            content: String,
            musicTitle: String,
            musicArtist: String
        ) = SendLetterCommand(
            senderId,
            recipientCode,
            title,
            content,
            musicTitle,
            musicArtist
        )
    }
}