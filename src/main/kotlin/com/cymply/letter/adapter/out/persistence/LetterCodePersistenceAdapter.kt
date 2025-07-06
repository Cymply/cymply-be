package com.cymply.letter.adapter.out.persistence

import com.cymply.letter.application.port.out.LoadLetterCodePort
import com.cymply.letter.application.port.out.SaveLetterCodePort
import com.cymply.letter.domain.LetterCode
import org.springframework.stereotype.Repository


@Repository
class LetterCodePersistenceAdapter(
    private val letterCodeJpaRepository: LetterCodeJapRepository,
    private val letterCodeEntityMapper: LetterCodeEntityMapper
) : LoadLetterCodePort, SaveLetterCodePort {
    override fun loadLetterCode(recipientId: Long): LetterCode? {
        val entity = letterCodeJpaRepository.findByRecipientId(recipientId)
        if (entity != null) {
            return letterCodeEntityMapper.to(entity)
        }
        return null
    }

    override fun saveLetterCode(letterCode: LetterCode): Long {
        val entity = letterCodeEntityMapper.to(letterCode)
        val result = letterCodeJpaRepository.save(entity)
        return result.id ?: 0
    }
}