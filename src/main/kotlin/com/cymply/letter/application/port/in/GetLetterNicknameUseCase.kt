package com.cymply.letter.application.port.`in`

import com.cymply.letter.application.dto.LetterNicknameInfo

interface GetLetterNicknameUseCase {
    fun getLetterNickname(senderId: Long, recipientCode: String): LetterNicknameInfo
}