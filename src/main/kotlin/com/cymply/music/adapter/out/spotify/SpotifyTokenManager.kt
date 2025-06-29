package com.cymply.music.adapter.out.spotify

import com.cymply.music.adapter.out.spotify.client.SpotifyAuthClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Instant

@Component
class SpotifyTokenManager(
    private val authClient: SpotifyAuthClient,
    @Value("\${spotify.client-id}")
    private val clientId: String,
    @Value("\${spotify.client-secret}")
    private val clientSecret: String,
) {

    private data class Token(val value: String, val expiresAt: Instant)

    private var token: Token? = null
    // 추후 redis 에서 token 관리하도록 수정

    fun getToken(): String {
        val now = Instant.now()
        val current = token

        if (current == null || now.isAfter(current.expiresAt)) {
            val newToken = authClient.requestToken(
                clientId = clientId,
                clientSecret = clientSecret
            )
            token = Token(newToken.accessToken, now.plusSeconds(newToken.expiresIn))
        }
        return token!!.value
    }
}