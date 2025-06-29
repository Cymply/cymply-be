package com.cymply.music.adapter.out.youtube.config

import com.cymply.music.application.port.out.YouTubeVideoSearchPort
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.SearchListResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class YouTubeVideoSearchAdapter(
    private val youTube: YouTube,
    @Value("\${google.api-key}")
    private val apiKey: String
) : YouTubeVideoSearchPort {

    override fun searchVideoIds(query: String, maxResult: Long): List<String> {
        val search: YouTube.Search.List = youTube.search()
            .list(listOf("snippet"))
            .setKey(apiKey)
            .setQ(query)
            .setType(listOf("video"))
            .setMaxResults(maxResult)

        val response: SearchListResponse = search.execute()

        return response.items
            .mapNotNull { it.id.videoId }
            .filter { it.isNotBlank() }
    }
}