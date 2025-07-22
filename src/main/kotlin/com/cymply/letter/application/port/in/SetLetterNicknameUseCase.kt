package com.cymply.letter.application.port.`in`

import com.cymply.letter.application.dto.SetLetterNicknameCommand

interface SetLetterNicknameUseCase {
    fun setLetterNickname(command: SetLetterNicknameCommand): Long
}