package com.cymply.letter.application.port.`in`

data class SetLetterNicknameCommand(
    val senderId: Long,
    val recipientCode: String,
    val nickname: String
)