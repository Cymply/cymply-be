package com.cymply.user.application.port.`in`


interface ValidateNicknameUseCase {
    fun validateNickname(nickname: String): Boolean
}