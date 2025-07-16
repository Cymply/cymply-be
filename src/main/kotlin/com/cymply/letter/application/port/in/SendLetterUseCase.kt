package com.cymply.letter.application.port.`in`


interface SendLetterUseCase {
    fun sendLetter(command: SendLetterCommand): Long
}