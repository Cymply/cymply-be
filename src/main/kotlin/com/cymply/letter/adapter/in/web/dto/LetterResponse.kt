package com.cymply.letter.adapter.`in`.web.dto

import com.cymply.letter.application.dto.GetLetterResult
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "편지 상세 응답 DTO")
data class LetterResponse(

    @field:Schema(description = "편지 ID", example = "1000")
    val id: Long,

    @field:Schema(description = "발신자 ID", example = "1000")
    val senderId: Long,

    @field:Schema(description = "편지 제목", example = "편지 제목입니다.")
    val title: String,

    @field:Schema(description = "편지 내용", example = "안녕 테스터!")
    val content: String,

    @field:Schema(description = "편지 발송 코드", example = "ABCD")
    val recipientCode: String?,

    @field:Schema(implementation = MusicResponse::class)
    val music: MusicResponse
) {
    companion object {
        fun from(result: GetLetterResult): LetterResponse =
            LetterResponse(
                id = result.id,
                senderId = result.senderId,
                title = result.title,
                content = result.content,
                recipientCode = result.recipientCode,
                music = MusicResponse(
                    title = result.musicTitle,
                    artist = result.musicArtist,
                    thumbnailUrl = result.musicThumbnailUrl,
                    videoUrl = result.musicVideoUrl
                )
            )
    }
}