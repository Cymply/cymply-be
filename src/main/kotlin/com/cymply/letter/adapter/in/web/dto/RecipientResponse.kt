package com.cymply.letter.adapter.`in`.web.dto

import com.cymply.letter.application.port.`in`.RecipientInfo
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "편지 수신자 정보 응답 DTO")
data class RecipientResponse(
    @field:Schema(description = "수신자 코드", example = "random")
    val code: String,

    @field:Schema(description = "수신자 이메일", example = "gildonghong@example.com")
    val email: String,

    @field:Schema(description = "수신자 닉네임", example = "홍길동")
    val nickname: String

) {
    companion object {
        fun from(info: RecipientInfo, code: String) = RecipientResponse(
            code = code,
            email = info.email,
            nickname = info.nickname
        )
    }
}