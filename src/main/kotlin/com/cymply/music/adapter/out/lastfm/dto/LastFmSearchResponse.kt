package com.cymply.music.adapter.out.lastfm.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class LastFmSearchResponse(
    val results: Results
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Results(
        @field:JsonProperty("opensearch:Query")
        val query: OpenSearchQuery?,
        @field:JsonProperty("opensearch:totalResults")
        val startIndex: Int?,
        @field:JsonProperty("opensearch:startIndex")
        val itemsPerPage: Int?,
        @field:JsonProperty("opensearch:itemsPerPage")
        val totalResults: Int,
        @field:JsonProperty("trackmatches")
        val trackMatches: TrackMatches,
        @field:JsonProperty("@attr")
        val attr: Map<String, Any>? = null
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class OpenSearchQuery(
        @field:JsonProperty("#text")
        val text: String,
        val role: String,
        @field:JsonProperty("startPage")
        val startPage: Int?
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class TrackMatches(
        val track: List<Track>
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Track(
        val name: String,
        val artist: String,
        val url: String,
        val streamable: String?,
        val listeners: Int,
        val image: List<Image>,
        val mbid: String?
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Image(
        @field:JsonProperty("#text")
        val url: String,
        val size: String
    )
}