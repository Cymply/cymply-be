package com.cymply.letter.application.port.`in`

interface SetNicknameUseCase {
    fun setNickname(command: SetNicknameCommand): Long
}