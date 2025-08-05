package com.cymply.user.application.port.out

import com.cymply.user.domain.RecipientCode

interface LoadRecipientCodePort {
    fun load(recipientId: Long): RecipientCode?

    fun load(code: String): RecipientCode
}