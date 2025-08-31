package com.cymply.user.application.port.`in`

import com.cymply.user.application.dto.UserSimpleInfo


interface GetUserUseCase {
    fun getActiveUserOrElseThrow(id: Long): UserSimpleInfo

    fun getActiveUser(provider: String, sub: String): UserSimpleInfo?
}