package com.cymply.letter.application.dto

import com.cymply.letter.domain.LetterNickname
import java.time.LocalDateTime

data class LetterNicknameInfo(
    val nickname: String,
    val createdAt: LocalDateTime

) {
    companion object {
        fun from(nickname: LetterNickname): LetterNicknameInfo {
            return LetterNicknameInfo(nickname.nickname, nickname.createdAt)
        }
    }
}