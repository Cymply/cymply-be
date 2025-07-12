package com.cymply.letter.application.service

import com.cymply.letter.application.port.`in`.GetLetterNicknameUseCase
import com.cymply.letter.application.port.`in`.LetterNicknameInfo
import com.cymply.user.application.port.`in`.GetRecipientUseCase
import com.cymply.letter.application.port.out.LoadLetterNicknamePort
import org.springframework.stereotype.Service

@Service
class GetLetterNicknameService(
    private val getRecipientUseCase: GetRecipientUseCase,
    private val loadLetterNicknamePort: LoadLetterNicknamePort,
) : GetLetterNicknameUseCase {
    override fun getLetterNickname(senderId: Long, recipientCode: String): LetterNicknameInfo {
        val recipient = getRecipientUseCase.getRecipient(recipientCode)
        val nickname = loadLetterNicknamePort.load(senderId, recipient.id)
            ?: throw IllegalStateException("Not found LetterNickname")

        return LetterNicknameInfo.from(nickname)
    }

}