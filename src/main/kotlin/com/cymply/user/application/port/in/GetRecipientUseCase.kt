package com.cymply.user.application.port.`in`

import com.cymply.user.application.dto.UserSimpleInfo

interface GetRecipientUseCase {
    fun getRecipient(code: String): UserSimpleInfo
}