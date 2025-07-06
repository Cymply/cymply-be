package com.cymply.letter.adapter.out.persistence

import com.cymply.letter.adapter.out.persistence.entity.LetterEntity
import org.springframework.data.repository.CrudRepository

interface LetterJapRepository : CrudRepository<LetterEntity, String> {
}