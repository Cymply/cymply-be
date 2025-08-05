package com.cymply.music.adapter.`in`.web.controller

import com.cymply.common.response.ApiResponse import com.cymply.music.adapter.`in`.web.dto.SearchMusicRequest
import com.cymply.music.adapter.`in`.web.dto.SearchMusicResponse
import com.cymply.music.adapter.`in`.web.dto.SearchMusicUrlRequest
import com.cymply.music.adapter.`in`.web.dto.SearchMusicUrlResponse
import com.cymply.music.application.port.`in`.PlayMusicUseCase
import com.cymply.music.application.port.`in`.SearchMusicUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/music")
class MusicController(
    private val searchMusicUseCase: SearchMusicUseCase,
    private val playMusicUseCase: PlayMusicUseCase
) : MusicApiController {

    @GetMapping("/search")
    override fun searchMusic(
        @ModelAttribute request: SearchMusicRequest
    ): ApiResponse<List<SearchMusicResponse>> {
        val result = searchMusicUseCase.searchMusic(request.toQuery())
        return ApiResponse.success(result.map { SearchMusicResponse.from(it) })
    }

    @GetMapping("/search/url")
    override fun searchMusicUrl(
        @ModelAttribute request: SearchMusicUrlRequest
    ): ApiResponse<SearchMusicUrlResponse> {
        val result = playMusicUseCase.playMusic(request.toQuery())
        return ApiResponse.success(SearchMusicUrlResponse.from(result))
    }

}