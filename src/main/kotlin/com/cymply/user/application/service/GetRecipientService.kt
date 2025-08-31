package com.cymply.user.application.service

import com.cymply.user.application.dto.UserSimpleInfo
import com.cymply.user.application.port.`in`.GetRecipientUseCase
import com.cymply.user.application.port.out.LoadRecipientCodePort
import com.cymply.user.application.port.out.LoadUserPort
import com.cymply.user.domain.User
import org.springframework.stereotype.Service

@Service
class GetRecipientService(
    private val loadRecipientCodePort: LoadRecipientCodePort,
    private val loadUserPort: LoadUserPort
) : GetRecipientUseCase {
    override fun getRecipient(code: String): UserSimpleInfo {
        val find = loadRecipientCodePort.load(code)
        find.verifyIsNotExpired()

        val user = loadUserPort.loadUserById(find.recipientId)
        user.verifyValidUser()

        return UserSimpleInfo(user.getIdOrThrow(), user.email, user.nickname, User.Role.USER)
    }
}