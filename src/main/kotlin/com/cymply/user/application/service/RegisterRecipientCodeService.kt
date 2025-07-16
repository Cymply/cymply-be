package com.cymply.user.application.service

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.cymply.user.application.port.`in`.RegisterRecipientCodeUseCase
import com.cymply.user.application.port.out.LoadRecipientCodePort
import com.cymply.user.application.port.out.SaveRecipientCodePort
import com.cymply.user.domain.RecipientCode
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

/**
 * 편지 수신 코드 생성
 * 수신자 아이디로 조회 후 존재하지 않은 경우 생성
 * 존재하는 경우 기존의 코드 반환
 */
@Service
class RegisterRecipientCodeService(
    val loadRecipientCodePort: LoadRecipientCodePort,
    val saveRecipientCodePort: SaveRecipientCodePort
) : RegisterRecipientCodeUseCase {
    @Transactional
    override fun register(recipientId: Long): String {
        val find = loadRecipientCodePort.load(recipientId)

        if (find != null) {
            find.verifyIsNotExpired()
            return find.code

        }

        val random = NanoIdUtils.randomNanoId()
        val code = RecipientCode.of(random, recipientId)

        saveRecipientCodePort.save(code)
        return code.code
    }
}