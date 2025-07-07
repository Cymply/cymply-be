package com.cymply.letter.application.port.`in`

interface GetRecipientUseCase {
    fun getRecipient(code: String): RecipientInfo
}