package com.cymply.letter.application.port.out

import com.cymply.letter.domain.LetterNickname

interface SaveLetterNicknamePort {
    fun saveLetterNickname(nickname: LetterNickname): Long
}