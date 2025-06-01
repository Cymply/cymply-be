package com.cymply.common.response

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "공통 API 응답 모델")
data class ApiResponse<T>(

    @field:Schema(description = "성공 여부")
    val success: Boolean,

    @field:Schema(description = "응답 데이터")
    val data: DataWrapper<T>? = null,

    @field:Schema(description = "에러 메시지")
    val errorMessage: String? = null
) {

    @Schema(description = "응답 데이터 래퍼")
    data class DataWrapper<T>(

        @field:Schema(description = "응답 데이터 내용")
        val content: T,

        @field:Schema(description = "페이징")
        val pagination: Pagination? = null
    )

    @Schema(description = "페이지 정보 DTO")
    data class Pagination(

        @field:Schema(description = "현재 페이지")
        val page: Int,

        @field:Schema(description = "페이지별 데이터 수")
        val size: Int,

        @field:Schema(description = "전체 페이지 수")
        val totalPages: Int,

        @field:Schema(description = "전체 데이터 수")
        val totalElements: Long
    )

    companion object {
        fun <T> success(content: T, pagination: Pagination? = null): ApiResponse<T> =
            ApiResponse(
                success = true,
                data = DataWrapper(content, pagination),
                errorMessage = null
            )

        fun <T> failure(content: T, errorMessage: String? = null): ApiResponse<T> =
            ApiResponse(
                success = false,
                data = null,
                errorMessage = errorMessage
            )
    }
}