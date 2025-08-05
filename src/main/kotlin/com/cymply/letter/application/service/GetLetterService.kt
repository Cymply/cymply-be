package com.cymply.letter.application.service

import com.cymply.letter.application.dto.*
import com.cymply.letter.application.port.`in`.GetLetterUseCase
import com.cymply.letter.application.port.out.LoadLetterPort
import com.cymply.letter.application.port.out.SaveLetterPort
import com.cymply.music.application.port.`in`.GetMusicUseCase
import org.springframework.stereotype.Service

@Service
class GetLetterService(
    private val loadLetterPort: LoadLetterPort,
    private val saveLetterPort: SaveLetterPort,
    private val getMusicUseCase: GetMusicUseCase,
) : GetLetterUseCase {

    override fun getLetter(query: GetLetterQuery): GetLetterResult {
        val letter = loadLetterPort.loadById(query.letterId)

        if (letter.recipientId != query.userId) throw IllegalArgumentException("당신 편지가 아니에용")

        if (!letter.isRead()) {
            letter.read()
            saveLetterPort.save(letter)
        }

        val music = getMusicUseCase.getMusic(letter.musicId)

        return GetLetterResult.toResult(letter, music)
    }

    override fun getLetters(userId: Long): List<LetterSummary> {
        return loadLetterPort.loadAllByUserId(userId)
    }

    override fun getCounts(userId: Long): GetLetterCountResult {
        val sentCount = loadLetterPort.loadSentCountByUserId(userId)
        val receivedCount = loadLetterPort.loadReceivedCountByUserId(userId)

        return GetLetterCountResult(sentCount, receivedCount)
    }
}