package com.cymply.user.application.port.`in`


interface JoinUserUseCase {
    fun joinUser(command: JoinUserCommand): Long
}