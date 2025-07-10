package com.cymply.user.adapter.out.persistence

import com.cymply.user.adapter.out.persistence.mapper.RecipientCodeEntityMapper
import com.cymply.user.adapter.out.persistence.repository.RecipientCodeJapRepository
import com.cymply.user.application.port.out.LoadRecipientCodePort
import com.cymply.user.application.port.out.SaveRecipientCodePort
import com.cymply.user.domain.RecipientCode
import org.springframework.stereotype.Repository


@Repository
class RecipientCodePersistenceAdapter(
    private val letterCodeJpaRepository: RecipientCodeJapRepository,
    private val recipientCodeEntityMapper: RecipientCodeEntityMapper
) : LoadRecipientCodePort, SaveRecipientCodePort {
    override fun save(code: RecipientCode): Long {
        val entity = recipientCodeEntityMapper.to(code)
        val result = letterCodeJpaRepository.save(entity)
        return result.id ?: 0
    }

    override fun load(recipientId: Long): RecipientCode? {
        letterCodeJpaRepository.findByRecipientId(recipientId)?.let {
            return recipientCodeEntityMapper.to(it)
        }
        return null
    }

    override fun load(code: String): RecipientCode {
        letterCodeJpaRepository.findByCode(code)?.let {
            return recipientCodeEntityMapper.to(it)
        }
        throw IllegalArgumentException("Not found letter code")
    }
}