package com.cymply.music.application.port.out

import com.cymply.music.domain.model.Music

interface SaveMusicPort {
    fun save(music: Music): Music
}