package com.cymply.user.adapter.out.persistence.repository

import com.cymply.user.adapter.out.persistence.entity.RecipientCodeEntity
import org.springframework.data.repository.CrudRepository

interface RecipientCodeJapRepository : CrudRepository<RecipientCodeEntity, String> {
    fun findByRecipientId(recipientId: Long): RecipientCodeEntity?

    fun findByCode(code: String): RecipientCodeEntity?
}