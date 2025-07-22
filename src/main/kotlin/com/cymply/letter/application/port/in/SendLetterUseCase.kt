package com.cymply.letter.application.port.`in`

import com.cymply.letter.application.dto.SendLetterCommand


interface SendLetterUseCase {
    fun sendLetter(command: SendLetterCommand): Long
}