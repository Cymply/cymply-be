package com.cymply.letter.application.dto

data class SendLetterCommand(
    val senderId: Long,
    val recipientCode: String,
    val content: String,
    val title: String,
    val artist: String,
) {
    companion object {
        fun of(senderId: Long, recipientCode: String, content: String, title: String, artist: String) =
            SendLetterCommand(senderId, recipientCode, content, title, artist)
    }
}