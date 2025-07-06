package com.cymply.letter.application.port.`in`

interface CreateLetterCodeUseCase {
    fun createLetterCode(recipientId: Long): String
}