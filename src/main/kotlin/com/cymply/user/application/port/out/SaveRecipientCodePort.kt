package com.cymply.user.application.port.out

import com.cymply.user.domain.RecipientCode

interface SaveRecipientCodePort {
    fun save(code: RecipientCode): Long
}