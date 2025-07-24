package com.cymply.letter.adapter.out.persistence

import com.cymply.letter.adapter.out.persistence.mapper.LetterEntityMapper
import com.cymply.letter.adapter.out.persistence.repository.LetterJpaRepository
import com.cymply.letter.adapter.out.persistence.repository.LetterQueryRepository
import com.cymply.letter.application.dto.LetterSummary
import com.cymply.letter.application.port.out.LoadLetterPort
import com.cymply.letter.application.port.out.SaveLetterPort
import com.cymply.letter.domain.Letter
import org.springframework.stereotype.Repository

@Repository
class LetterPersistenceAdapter(
    private val letterJpaRepository: LetterJpaRepository,
    private val letterQueryRepository: LetterQueryRepository,
    private val letterEntityMapper: LetterEntityMapper
) : LoadLetterPort, SaveLetterPort {
    override fun loadById(id: Long): Letter {
        val entity = letterJpaRepository.findById(id)

        if (entity != null) {
            return letterEntityMapper.to(entity)
        }
        throw IllegalArgumentException("Not found letter By Id")
    }

    override fun loadSentCountByUserId(userId: Long): Long {
        return letterJpaRepository.countBySenderId(userId)
    }

    override fun loadReceivedCountByUserId(userId: Long): Long {
        return letterJpaRepository.countByRecipientId(userId)
    }

    override fun loadAllByUserId(userId: Long): List<LetterSummary> {
        return letterQueryRepository.findLettersByRecipientId(userId)
    }

    override fun save(letter: Letter): Long {
        val entity = letterEntityMapper.to(letter)
        val result = letterJpaRepository.save(entity)
        return result.id ?: -1
    }

}
