package com.cymply.music.application.port.out

import com.cymply.music.application.port.dto.LastFmSearchRequest
import com.cymply.music.domain.model.Music

interface LastFmPort {
    fun searchMusic(request: LastFmSearchRequest): List<Music>
}