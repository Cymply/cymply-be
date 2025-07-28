package com.cymply.letter.application.dto

data class SetLetterNicknameCommand(
    val senderId: Long,
    val recipientCode: String,
    val nickname: String
)