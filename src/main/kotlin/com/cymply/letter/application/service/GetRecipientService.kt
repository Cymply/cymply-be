package com.cymply.letter.application.service

import com.cymply.letter.application.port.`in`.GetRecipientUseCase
import com.cymply.letter.application.port.`in`.RecipientInfo
import com.cymply.letter.application.port.out.LoadLetterCodePort
import com.cymply.user.application.port.`in`.GetUserUseCase
import org.springframework.stereotype.Service

@Service
class GetRecipientService(
    private val loadLetterCodePort: LoadLetterCodePort,
    private val getUserUseCase: GetUserUseCase
) : GetRecipientUseCase {
    override fun getRecipient(code: String): RecipientInfo {
        loadLetterCodePort.loadLetterCode(code).let {
            it.verifyIsNotExpired()
            val user = getUserUseCase.getActiveUserOrElseThrow(it.recipientId)
            return RecipientInfo(user.email, user.nickname)
        }
    }
}