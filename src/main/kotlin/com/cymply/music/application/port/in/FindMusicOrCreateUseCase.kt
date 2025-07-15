package com.cymply.music.application.port.`in`

import com.cymply.music.application.port.dto.MusicSimpleInfo
import com.cymply.music.application.port.dto.PlayMusicQuery


interface FindMusicOrCreateUseCase {
    fun findMusicOrCreate(query: PlayMusicQuery): MusicSimpleInfo
}