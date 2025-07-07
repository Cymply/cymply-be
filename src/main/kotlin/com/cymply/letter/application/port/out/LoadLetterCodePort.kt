package com.cymply.letter.application.port.out

import com.cymply.letter.domain.LetterCode

interface LoadLetterCodePort {
    fun loadLetterCode(recipientId: Long): LetterCode?

    fun loadLetterCode(code: String): LetterCode
}