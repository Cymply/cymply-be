package com.cymply.letter.adapter.out.persistence

import com.cymply.letter.adapter.out.persistence.entity.LetterNicknameEntity
import org.springframework.data.repository.CrudRepository

interface LetterNicknameJapRepository : CrudRepository<LetterNicknameEntity, Long> {
    fun findBySenderIdAndRecipientId(senderId: Long, recipientId: Long): LetterNicknameEntity?
}