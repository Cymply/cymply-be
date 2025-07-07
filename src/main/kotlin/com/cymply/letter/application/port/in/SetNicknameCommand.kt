package com.cymply.letter.application.port.`in`

data class SetNicknameCommand(
    val senderId: Long,
    val code: String,
    val nickname: String
)