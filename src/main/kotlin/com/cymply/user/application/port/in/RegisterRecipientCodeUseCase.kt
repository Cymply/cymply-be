package com.cymply.user.application.port.`in`

interface RegisterRecipientCodeUseCase {
    fun register(recipientId: Long): String
}