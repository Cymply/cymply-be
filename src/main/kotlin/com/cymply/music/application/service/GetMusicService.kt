package com.cymply.music.application.service

import com.cymply.music.application.port.dto.GetMusicResult
import com.cymply.music.application.port.`in`.GetMusicUseCase
import com.cymply.music.application.port.out.LoadMusicPort
import org.springframework.stereotype.Service

@Service
class GetMusicService(
    private val loadMusicPort: LoadMusicPort
) : GetMusicUseCase {
    override fun getMusic(id: Long): GetMusicResult {
        val music = loadMusicPort.loadById(id)
        return GetMusicResult.from(music)
    }
}