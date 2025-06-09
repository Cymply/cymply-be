package com.cymply.user.application.port.`in`

import com.cymply.user.application.service.UserSimpleInfo


interface GetUserUseCase {
    fun getActiveUser(provider: String, sub: String): UserSimpleInfo?
}