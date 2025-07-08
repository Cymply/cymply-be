package com.cymply.letter.adapter.out.persistence

import com.cymply.letter.adapter.out.persistence.mapper.LetterCodeEntityMapper
import com.cymply.letter.adapter.out.persistence.repository.LetterCodeJapRepository
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
        letterCodeJpaRepository.findByRecipientId(recipientId)?.let {
            return letterCodeEntityMapper.to(it)
        }
        return null
    }

    override fun loadLetterCode(code: String): LetterCode {
        letterCodeJpaRepository.findByCode(code)?.let {
            return letterCodeEntityMapper.to(it)
        }
        throw IllegalArgumentException("Not found letter code")
    }

    override fun saveLetterCode(letterCode: LetterCode): Long {
        val entity = letterCodeEntityMapper.to(letterCode)
        val result = letterCodeJpaRepository.save(entity)
        return result.id ?: 0
    }
}