package com.cymply.letter.adapter.out.persistence


import com.cymply.letter.adapter.out.persistence.mapper.LetterNicknameEntityMapper
import com.cymply.letter.adapter.out.persistence.repository.LetterNicknameJapRepository
import com.cymply.letter.application.port.out.LoadLetterNicknamePort
import com.cymply.letter.application.port.out.SaveLetterNicknamePort
import com.cymply.letter.domain.LetterNickname
import org.springframework.stereotype.Repository


@Repository
class LetterNicknamePersistenceAdapter(
    private val letterNicknameJpaRepository: LetterNicknameJapRepository,
    private val letterNicknameEntityMapper: LetterNicknameEntityMapper
) : LoadLetterNicknamePort, SaveLetterNicknamePort {

    override fun load(senderId: Long, recipientId: Long): LetterNickname? {
        val entity = letterNicknameJpaRepository.findBySenderIdAndRecipientId(senderId, recipientId)

        if (entity != null) {
            return letterNicknameEntityMapper.to(entity)
        }

        return null
    }

    override fun save(nickname: LetterNickname): Long {
        val entity = letterNicknameEntityMapper.to(nickname)
        val result = letterNicknameJpaRepository.save(entity)
        return result.id ?: -1
    }

}
