package com.cymply.music.application.port.out

import com.cymply.music.application.port.dto.MusicSearchRequest
import com.cymply.music.domain.model.Music

interface SearchMusicPort {
    fun search(request: MusicSearchRequest): List<Music>
}