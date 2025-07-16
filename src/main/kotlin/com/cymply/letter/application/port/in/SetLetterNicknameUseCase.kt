package com.cymply.letter.application.port.`in`

interface SetLetterNicknameUseCase {
    fun setLetterNickname(command: SetLetterNicknameCommand): Long
}