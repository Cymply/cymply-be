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

    override fun loadLetterNickname(senderId: Long, recipientId: Long): LetterNickname? {
        letterNicknameJpaRepository.findBySenderIdAndRecipientId(senderId, recipientId)?.let {
            return letterNicknameEntityMapper.to(it)
        }
        return null
    }

    override fun saveLetterNickname(nickname: LetterNickname): Long {
        val entity = letterNicknameEntityMapper.to(nickname)
        val result = letterNicknameJpaRepository.save(entity)
        return result.id ?: -1
    }

}
