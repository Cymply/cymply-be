package com.cymply.music.application.port.`in`

import com.cymply.music.application.port.dto.SearchMusicQuery
import com.cymply.music.application.port.dto.SearchMusicResult

interface SearchMusicUseCase {

    fun searchMusic(query: SearchMusicQuery): List<SearchMusicResult>
}