package com.cymply.music.adapter.out.lastfm

import com.cymply.music.adapter.out.lastfm.client.LastFmApiClient
import com.cymply.music.application.port.dto.MusicSearchRequest
import com.cymply.music.application.port.out.SearchMusicPort
import com.cymply.music.domain.model.Music
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class LastFmSearchAdapter(
    private val apiClient: LastFmApiClient,
    @Value("\${lastfm.api-key}")
    private val apiKey: String
): SearchMusicPort {

    override fun search(request: MusicSearchRequest): List<Music> {
        val response = apiClient.searchTracks(
            keyword = request.keyword,
            apiKey = apiKey,
            limit = request.size,
            page = request.page
        )
        return response.results.trackMatches.track.map { track ->
            Music(
                title = track.name,
                artist = track.artist,
                album = null,
                thumbnailUrl = null
            )
        }
    }
}