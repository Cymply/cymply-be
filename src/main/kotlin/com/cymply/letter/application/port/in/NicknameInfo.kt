package com.cymply.letter.application.port.`in`

import java.time.LocalDateTime

data class NicknameInfo(
    val nickname: String,
    val createAt: LocalDateTime
)