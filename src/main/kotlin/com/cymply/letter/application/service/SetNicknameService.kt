package com.cymply.letter.application.service

import com.cymply.user.application.port.`in`.GetRecipientUseCase
import com.cymply.letter.application.port.`in`.SetNicknameCommand
import com.cymply.letter.application.port.`in`.SetNicknameUseCase
import com.cymply.letter.application.port.out.LoadLetterNicknamePort
import com.cymply.letter.application.port.out.SaveLetterNicknamePort
import com.cymply.letter.domain.LetterNickname
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SetNicknameService(
    private val getRecipientUseCase: GetRecipientUseCase,
    private val loadLetterNicknamePort: LoadLetterNicknamePort,
    private val saveLetterNicknamePort: SaveLetterNicknamePort
) : SetNicknameUseCase {

    @Transactional
    override fun setNickname(command: SetNicknameCommand): Long {
        val recipient = getRecipientUseCase.getRecipient(command.code)
        val exist = loadLetterNicknamePort.loadLetterNickname(command.senderId, recipient.id)
        if (exist != null) {
            throw IllegalArgumentException("Already set nickname")
        }

        val nickname = LetterNickname.of(command.senderId, recipient.id, command.nickname)
        return saveLetterNicknamePort.saveLetterNickname(nickname)
    }
}