package com.cymply.letter.application.service

import com.cymply.user.application.port.`in`.GetRecipientUseCase
import com.cymply.letter.application.port.`in`.SetLetterNicknameCommand
import com.cymply.letter.application.port.`in`.SetLetterNicknameUseCase
import com.cymply.letter.application.port.out.LoadLetterNicknamePort
import com.cymply.letter.application.port.out.SaveLetterNicknamePort
import com.cymply.letter.domain.LetterNickname
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SetLetterNicknameService(
    private val getRecipientUseCase: GetRecipientUseCase,
    private val loadLetterNicknamePort: LoadLetterNicknamePort,
    private val saveLetterNicknamePort: SaveLetterNicknamePort
) : SetLetterNicknameUseCase {

    @Transactional
    override fun setLetterNickname(command: SetLetterNicknameCommand): Long {
        val recipient = getRecipientUseCase.getRecipient(command.recipientCode)
        val find = loadLetterNicknamePort.load(command.senderId, recipient.id)

        if (find != null) {
            throw IllegalArgumentException("이미 닉네임을 설정했습니다.")
        }

        val nickname = LetterNickname.of(command.senderId, recipient.id, command.nickname)
        return saveLetterNicknamePort.save(nickname)
    }
}