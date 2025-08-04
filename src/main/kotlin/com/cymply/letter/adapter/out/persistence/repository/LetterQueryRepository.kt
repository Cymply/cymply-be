package com.cymply.letter.adapter.out.persistence.repository

import com.cymply.letter.adapter.out.persistence.entity.QLetterEntity
import com.cymply.letter.application.dto.LetterSummary
import com.cymply.music.adapter.out.entity.QMusicEntity
import com.cymply.user.adapter.out.persistence.entity.QUserEntity
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class LetterQueryRepository(
    private val queryFactory: JPAQueryFactory
) {
    fun findLettersByRecipientId(recipientId: Long): List<LetterSummary> {
        val qLetter = QLetterEntity.letterEntity
        val qUser = QUserEntity.userEntity
        val qMusic = QMusicEntity.musicEntity

        return queryFactory
            .select(
                Projections.constructor(
                    LetterSummary::class.java,
                    qLetter.senderId,
                    qUser.nickname,
                    qLetter.id,
                    qMusic.title,
                    qMusic.artist,
                    qMusic.thumbnail,
                    qMusic.videoUrl,
                    qLetter.title,
                    qLetter.content,
                    qLetter.createdAt
                )
            )
            .from(qLetter)
            .join(qUser).on(qLetter.senderId.eq(qUser.id))
            .join(qMusic).on(qLetter.musicId.eq(qMusic.id))
            .where(qLetter.recipientId.eq(recipientId))
            .fetch()
    }
}