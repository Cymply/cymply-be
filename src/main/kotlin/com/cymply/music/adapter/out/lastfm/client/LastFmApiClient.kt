package com.cymply.music.adapter.out.lastfm.client

import com.cymply.music.adapter.out.lastfm.dto.LastFmSearchResponse
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange

@HttpExchange
interface LastFmApiClient {

    @GetExchange(
        value = "/2.0"
    )
    fun searchTracks(
        @RequestParam("method")
        method: String = "track.search",
        @RequestParam("track")
        keyword: String,
        @RequestParam("api_key")
        apiKey: String,
        @RequestParam("format")
        format: String = "json",
        @RequestParam("limit")
        limit: Int,
        @RequestParam("page")
        page: Int
    ) : LastFmSearchResponse
}