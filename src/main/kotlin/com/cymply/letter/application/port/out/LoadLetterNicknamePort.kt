package com.cymply.letter.application.port.out

import com.cymply.letter.domain.LetterNickname


interface LoadLetterNicknamePort {
    fun loadLetterNickname(senderId: Long, recipientId: Long): LetterNickname?
}