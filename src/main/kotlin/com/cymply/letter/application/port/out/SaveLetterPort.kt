package com.cymply.letter.application.port.out

import com.cymply.letter.domain.Letter

interface SaveLetterPort {
    fun save(letter: Letter): Long
}