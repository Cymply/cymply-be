package com.cymply.letter.adapter.`in`.web.dto

import com.cymply.letter.application.dto.LetterNicknameInfo
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "송신자 닉네임 조회 DTO")
data class LetterNicknameResponse(
    @Schema(description = "송신자 닉네임", example = "홍길동")
    val nickname: String,

    @Schema(description = "닉네임 설정일", example = "2020-01-01T00:00:00")
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(info: LetterNicknameInfo): LetterNicknameResponse {
            return LetterNicknameResponse(info.nickname, info.createdAt)
        }
    }
}