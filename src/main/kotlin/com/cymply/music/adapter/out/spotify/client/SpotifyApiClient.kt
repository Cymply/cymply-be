package com.cymply.music.adapter.out.spotify.client

import com.cymply.music.adapter.out.spotify.dto.SpotifySearchResponse
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange

@HttpExchange
interface SpotifyApiClient {

    @GetExchange(
        value = "/v1/search",
    )
    fun searchTracks(
        @RequestHeader(HttpHeaders.AUTHORIZATION)
        authorization: String,
        @RequestParam("q")
        keyword: String,
        @RequestParam("type")
        type: String,
        @RequestParam("limit")
        limit: Int = 10,
        @RequestParam("offset")
        offset: Int = 0,
        @RequestParam("locale")
        locale: String = "ko-KR"
    ): SpotifySearchResponse

}