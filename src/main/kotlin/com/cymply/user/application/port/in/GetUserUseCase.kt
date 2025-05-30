package com.cymply.user.application.port.`in`


interface GetUserUseCase {
    fun getUserByEmail(email: String): UserInfo?

    fun getActiveUser(provider: String, sub: String): UserInfo?
}