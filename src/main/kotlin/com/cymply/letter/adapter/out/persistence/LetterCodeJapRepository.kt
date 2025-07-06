package com.cymply.letter.adapter.out.persistence

import com.cymply.letter.adapter.out.persistence.entity.LetterCodeEntity
import org.springframework.data.repository.CrudRepository

interface LetterCodeJapRepository : CrudRepository<LetterCodeEntity, String> {
    fun findByRecipientId(recipientId: Long): LetterCodeEntity?
}