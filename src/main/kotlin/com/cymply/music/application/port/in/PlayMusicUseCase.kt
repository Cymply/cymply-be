package com.cymply.music.application.port.`in`

import com.cymply.music.application.port.dto.PlayMusicQuery
import com.cymply.music.application.port.dto.PlayMusicResult

interface PlayMusicUseCase {

    fun playMusic(query: PlayMusicQuery): PlayMusicResult
}