package com.cymply.letter.application.port.out

import com.cymply.letter.domain.LetterCode

interface SaveLetterCodePort {
    fun saveLetterCode(letterCode: LetterCode): Long
}