package com.cymply.music.application.port.`in`

import com.cymply.music.application.port.dto.GetMusicResult

interface GetMusicUseCase {
    fun getMusic(id: Long): GetMusicResult
}