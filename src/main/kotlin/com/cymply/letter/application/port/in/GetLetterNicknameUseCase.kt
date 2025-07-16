package com.cymply.letter.application.port.`in`


interface GetLetterNicknameUseCase {
    fun getLetterNickname(senderId: Long, recipientCode: String): LetterNicknameInfo
}