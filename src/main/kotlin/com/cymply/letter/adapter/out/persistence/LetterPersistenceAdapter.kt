package com.cymply.letter.adapter.out.persistence


import com.cymply.letter.adapter.out.persistence.mapper.LetterEntityMapper
import com.cymply.letter.adapter.out.persistence.repository.LetterJapRepository
import com.cymply.letter.application.port.out.SaveLetterPort
import com.cymply.letter.domain.Letter
import org.springframework.stereotype.Repository


@Repository
class LetterPersistenceAdapter(
    private val letterJpaRepository: LetterJapRepository,
    private val letterEntityMapper: LetterEntityMapper
) : SaveLetterPort {
    override fun save(letter: Letter): Long {
        val entity = letterEntityMapper.to(letter)
        val result = letterJpaRepository.save(entity)
        return result.id ?: -1
    }

}
