package com.cymply.music.adapter.`in`.web.controller

import com.cymply.music.adapter.`in`.web.dto.MusicResponse
import com.cymply.music.adapter.`in`.web.dto.MusicSearchRequest
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Tag(name = "Music", description = "음악 관련 API")
@RequestMapping("/api/v1/music")
interface MusicApiController {

    @Operation(
        summary = "음악 검색",
        description = "제목 또는 아티스트 이름으로 음악을 검색합니다."
    )
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
            ),
            ApiResponse(responseCode = "404", description = "")
        ]
    )
    @GetMapping("/search")
    fun searchMusic(
        @ParameterObject @RequestParam request: MusicSearchRequest
    ) : com.cymply.common.response.ApiResponse<List<MusicResponse>>

}