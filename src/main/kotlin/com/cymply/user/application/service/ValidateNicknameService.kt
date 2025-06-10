package com.cymply.user.application.service

import com.cymply.user.application.port.`in`.ValidateNicknameUseCase
import com.cymply.user.application.port.out.LoadUserPort
import org.springframework.stereotype.Service

@Service
class ValidateNicknameService(
    private val loadUserPort: LoadUserPort
) : ValidateNicknameUseCase {
    override fun validateNickname(nickname: String): Boolean {
        TODO("Not yet implemented")
    }

}