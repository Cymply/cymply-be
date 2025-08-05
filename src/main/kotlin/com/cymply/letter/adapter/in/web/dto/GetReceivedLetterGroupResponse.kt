package com.cymply.letter.adapter.`in`.web.dto

import com.cymply.letter.application.dto.LetterSummary
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "발신자 별 편지 목록 DTO")
data class GetReceivedLetterGroupResponse(

    @field:Schema(description = "편지 ID", example = "1000")
    val senderId: Long,

    @field:Schema(description = "발신자 이름", example = "홍길동")
    val senderName: String,

    @field:ArraySchema(
        arraySchema = Schema(description = "편지 목록"),
        schema      = Schema(implementation = ReceivedLetterDetailResponse::class)
    )
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
                                title = it.title,
                                sentAt = it.sentAt,
                                isRead = it.readAt != null
                            )
                        }
                    )
                }
        }
    }
}

@Schema(description = "발신자 별 편지 목록 DTO")
data class ReceivedLetterDetailResponse(

    @field:Schema(description = "편지 ID", example = "1000")
    val letterId: Long,

    @field:Schema(description = "음악 제목", example = "좋은 날")
    val musicTitle: String,

    @field:Schema(description = "음악 아티스트", example = "아이유")
    val musicArtist: String,

    @field:Schema(description = "음악 썸네일 URL", example = "http://example.com")
    val musicThumbnailUrl: String,

    @field:Schema(description = "음악 재생 URL", example = "http://example.com")
    val videoUrl: String,

    @field:Schema(description = "편지 제목", example = "편지 제목입니다.")
    val title: String,

    @field:Schema(description = "편지 내용", example = "안녕 친구야")
    val content: String,

    @field:Schema(description = "편지 전송 시각", example = "2025-07-09 00:00:00")
    val sentAt: LocalDateTime,

    @field:Schema(description = "편지 읽음 여부", example = "true")
    val isRead: Boolean
)
