package com.cymply.music.adapter.out.spotify.client

import com.cymply.music.adapter.out.spotify.dto.SpotifyAuthResponse
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange

@HttpExchange
interface SpotifyAuthClient {

    @PostExchange(
        value = "/api/token",
        contentType = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    fun requestToken(
        @RequestParam("grant_type")
        grantType: String = "client_credentials",
        @RequestParam("client_id")
        clientId: String,
        @RequestParam("client_secret")
        clientSecret: String
    ): SpotifyAuthResponse
}