package com.cymply.music.adapter.out.spotify

import com.cymply.music.adapter.out.spotify.client.SpotifyApiClient
import com.cymply.music.application.port.dto.MusicSearchRequest
import com.cymply.music.application.port.out.SearchMusicPort
import com.cymply.music.domain.model.Music
import org.springframework.stereotype.Component

@Component
class SpotifySearchAdapter(
    private val apiClient: SpotifyApiClient,
    private val tokenManager: SpotifyTokenManager
): SearchMusicPort {

    override fun search(request: MusicSearchRequest): List<Music> {
        val token = tokenManager.getToken()
        val response = apiClient.searchTracks(
            authorization = "Bearer $token",
            keyword = request.keyword,
            type = "track",
            limit = request.size,
            offset = request.page - 1
        )

        return response.tracks.items.map { item ->
            Music(
                title = item.name,
                artist = item.artists.joinToString(", ") { it.name },
                album = item.album.name,
                thumbnailUrl = item.album.images.maxByOrNull { it.height }?.url
            )
        }
    }
}