package com.cymply.music.adapter.out.lastfm

import com.cymply.music.adapter.out.lastfm.dto.LastFmSearchResponse
import com.cymply.music.application.port.dto.LastFmSearchRequest
import com.cymply.music.application.port.out.LastFmPort
import com.cymply.music.domain.model.Music
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient

@Component
class LastFmClient(
    private val restClient: RestClient,

    @Value("\${api-key.last-fm}")
    private val lastFmApiKey: String
) : LastFmPort {

    override fun searchMusic(request: LastFmSearchRequest): List<Music> {
        val entity = restClient.get()
            .uri { builder ->
                builder.path("2.0")
                    .queryParam("method", "track.search")
                    .queryParam("track", request.keyword)
                    .queryParam("api_key", lastFmApiKey)
                    .queryParam("format", "json")
                    .queryParam("limit", request.limit)
                    .queryParam("page", request.page)
                    .build()
            }
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .toEntity(LastFmSearchResponse::class.java)

        if (!entity.statusCode.is2xxSuccessful || entity.body == null) {
            throw IllegalStateException("${entity.statusCode}")
        }

        val body = entity.body!!

        return body.results.trackMatches.track.map { t ->
            Music(
                title = t.name,
                artist = t.artist,
                album = null,
                thumbnailUrl = null
            )
        }
    }
}