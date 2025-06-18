package com.cymply.music.application.service

import com.cymply.music.application.port.dto.LastFmSearchRequest
import com.cymply.music.application.port.dto.SearchMusicQuery
import com.cymply.music.application.port.dto.SearchMusicResult
import com.cymply.music.application.port.`in`.SearchMusicUseCase
import com.cymply.music.application.port.out.LastFmPort
import org.springframework.stereotype.Service

@Service
class SearchMusicService(
    private val lastFmPort: LastFmPort
) : SearchMusicUseCase {

    override fun searchMusic(query: SearchMusicQuery): List<SearchMusicResult> {
        val tracks = lastFmPort.searchMusic(LastFmSearchRequest.toRequest(query))

        return tracks.map { SearchMusicResult.toResult(it) }
    }
}