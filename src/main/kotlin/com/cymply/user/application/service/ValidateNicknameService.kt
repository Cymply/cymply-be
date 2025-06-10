package com.cymply.user.application.service

import com.cymply.user.application.port.`in`.ValidateNicknameUseCase
import com.cymply.user.application.port.out.LoadUserPort
import org.springframework.stereotype.Service

@Service
class ValidateNicknameService(
    private val loadUserPort: LoadUserPort
) : ValidateNicknameUseCase {
    /**
     * 1 ~ 10 길이
     * 영어 대소문자, 한글, 특수문자(_, #, !) 사용 가능
     * 이미 사용중인 닉네임 사용 불가
     */
    override fun validateNickname(nickname: String): Boolean {
        if (nickname.isBlank() || nickname.length > 10) {
            return false
        }

        val regx = Regex("^[A-Za-zㄱ-ㅎ가-힣_#!]+$")
        if (!nickname.matches(regx)) {
            return false
        }

        val user = loadUserPort.loadUserByNickname(nickname)
        return user == null
    }
}