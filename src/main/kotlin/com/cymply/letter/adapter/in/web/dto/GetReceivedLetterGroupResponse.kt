package com.cymply.letter.adapter.`in`.web.dto

import com.cymply.letter.application.dto.LetterSummary
import java.time.LocalDateTime

data class GetReceivedLetterGroupResponse(
    val senderId: Long,
    val senderName: String,
    val letters: List<ReceivedLetterDetailResponse>
) {
    companion object {
        fun from(summary: List<LetterSummary>): List<GetReceivedLetterGroupResponse> {
            return summary
                .groupBy { it.senderId to it.senderName }
                .map { (key, group) ->
                    val (senderId, senderName) = key
                    GetReceivedLetterGroupResponse(
                        senderId = senderId,
                        senderName = senderName,
                        letters = group.map {
                            ReceivedLetterDetailResponse(
                                letterId = it.letterId,
                                musicTitle = it.musicTitle,
                                musicArtist = it.musicArtist,
                                musicThumbnailUrl = it.musicThumbnailUrl,
                                videoUrl = it.videoUrl,
                                content = it.content,
                                sentAt = it.sentAt
                            )
                        }
                    )
                }
        }
    }
}

data class ReceivedLetterDetailResponse(
    val letterId: Long,
    val musicTitle: String,
    val musicArtist: String,
    val musicThumbnailUrl: String,
    val videoUrl: String,
    val content: String,
    val sentAt: LocalDateTime
)
