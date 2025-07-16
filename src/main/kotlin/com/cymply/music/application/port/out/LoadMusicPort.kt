package com.cymply.music.application.port.out

import com.cymply.music.domain.model.Music

interface LoadMusicPort {
    fun loadById(id: Long): Music

    fun loadByTitleAndArtist(title: String, artist: String): Music?
}