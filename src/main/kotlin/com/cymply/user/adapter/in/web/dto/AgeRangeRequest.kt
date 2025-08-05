package com.cymply.user.adapter.`in`.web.dto

import io.swagger.v3.oas.annotations.media.Schema

enum class AgeRangeRequest {
    @Schema(description = "9세 이하")
    AGE_UNDER_10,

    @Schema(description = "10~19세")
    AGE_10_19,

    @Schema(description = "20~24세")
    AGE_20_24,

    @Schema(description = "25~30세")
    AGE_25_30,

    @Schema(description = "31세 이상")
    AGE_OVER_30;
}
