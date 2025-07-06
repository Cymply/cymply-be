package com.cymply.letter.application.service

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.cymply.letter.application.port.`in`.CreateLetterCodeUseCase
import com.cymply.letter.application.port.out.LoadLetterCodePort
import com.cymply.letter.application.port.out.SaveLetterCodePort
import com.cymply.letter.domain.LetterCode
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

/**
 * 편지 수신 코드 생성
 * 수신자 아이디로 조회 후 존재하지 않은 경우 생성
 * 존재하는 경우 기존의 코드 반환
 */
@Service
class CreateLetterCodeService(
    val loadLetterCodePort: LoadLetterCodePort,
    val saveLetterCodePort: SaveLetterCodePort
) : CreateLetterCodeUseCase {

    @Transactional
    override fun createLetterCode(recipientId: Long): String {
        val exist = loadLetterCodePort.loadLetterCode(recipientId)
        if (exist == null) {
            val code = LetterCode.of(recipientId, NanoIdUtils.randomNanoId())
            saveLetterCodePort.saveLetterCode(code)
            return code.code
        }

        exist.verifyIsNotExpired()
        return exist.code
    }
}