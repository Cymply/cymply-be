package com.cymply.music.application.service

import com.cymply.music.application.port.dto.MusicSearchRequest
import com.cymply.music.application.port.dto.SearchMusicQuery
import com.cymply.music.application.port.dto.SearchMusicResult
import com.cymply.music.application.port.`in`.SearchMusicUseCase
import com.cymply.music.application.port.out.SearchMusicPort
import org.springframework.stereotype.Service

@Service
class SearchMusicService(
    private val searchMusicPort: SearchMusicPort,
) : SearchMusicUseCase {

    override fun searchMusic(query: SearchMusicQuery): List<SearchMusicResult> {
        val musics = searchMusicPort.search(MusicSearchRequest.toRequest(query))

        return musics.map { SearchMusicResult.toResult(it) }
    }
}