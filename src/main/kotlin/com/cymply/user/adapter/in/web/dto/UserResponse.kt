package com.cymply.user.adapter.`in`.web.dto

import com.cymply.user.application.dto.UserSimpleInfo
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "회원 정보 응답 DTO")
data class UserResponse(
    @field:Schema(description = "회원 ID", example = "1")
    val id: Long,

    @field:Schema(description = "회원 이메일", example = "gildonghong@gmail.com")
    val email: String,

    @field:Schema(description = "회원 닉네임", example = "gildonghong")
    val nickname: String? = null
) {
    companion object {
        fun from(info: UserSimpleInfo) =
            UserResponse(info.id, info.email, info.nickname)
    }
}