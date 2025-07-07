package com.cymply.letter.application.service

import com.cymply.letter.application.port.`in`.SetNicknameCommand
import com.cymply.letter.application.port.`in`.SetNicknameUseCase
import com.cymply.letter.application.port.out.LoadLetterCodePort
import com.cymply.letter.application.port.out.LoadLetterNicknamePort
import com.cymply.letter.application.port.out.SaveLetterNicknamePort
import com.cymply.letter.domain.LetterNickname
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class SetNicknameService(
    private val loadLetterCodePort: LoadLetterCodePort,
    private val loadLetterNicknamePort: LoadLetterNicknamePort,
    private val saveLetterNicknamePort: SaveLetterNicknamePort
) : SetNicknameUseCase {

    @Transactional
    override fun setNickname(command: SetNicknameCommand): Long {
        val code = loadLetterCodePort.loadLetterCode(command.code)
        val exist = loadLetterNicknamePort.loadLetterNickname(command.senderId, code.recipientId)
        if (exist != null) {
            throw IllegalArgumentException("Already set nickname")
        }

        val nickname = LetterNickname.of(command.senderId, code.recipientId, command.nickname)
        return saveLetterNicknamePort.saveLetterNickname(nickname)
    }
}