package com.cymply.music.adapter.`in`.web.controller

import com.cymply.common.response.ApiResponse
import com.cymply.music.adapter.`in`.web.dto.MusicResponse
import com.cymply.music.adapter.`in`.web.dto.MusicSearchRequest
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/music")
class MusicController : MusicApiController {

    override fun searchMusic(
        @RequestParam request: MusicSearchRequest
    ): ApiResponse<List<MusicResponse>> {

        return ApiResponse.success(emptyList())
    }
}