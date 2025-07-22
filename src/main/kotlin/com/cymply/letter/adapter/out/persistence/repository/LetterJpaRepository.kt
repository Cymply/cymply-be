package com.cymply.letter.adapter.out.persistence.repository

import com.cymply.letter.adapter.out.persistence.entity.LetterEntity
import org.springframework.data.repository.CrudRepository

interface LetterJpaRepository : CrudRepository<LetterEntity, String> {
    fun findById(id: Long): LetterEntity?
    fun countBySenderId(senderId: Long): Long
    fun countByRecipientId(recipientId: Long): Long
}