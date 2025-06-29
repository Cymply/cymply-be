package com.cymply.music.application.service

import com.cymply.music.application.port.dto.PlayMusicQuery
import com.cymply.music.application.port.dto.PlayMusicResult
import com.cymply.music.application.port.`in`.PlayMusicUseCase
import com.cymply.music.application.port.out.YouTubeVideoSearchPort
import org.springframework.stereotype.Service

@Service
class PlayMusicService(
    private val youTubePort: YouTubeVideoSearchPort
) : PlayMusicUseCase {

    override fun playMusic(query: PlayMusicQuery): PlayMusicResult {
        val ids = youTubePort.searchVideoIds(query = "${query.artist} ${query.title}", maxResult = 1)
        val videoId = ids.firstOrNull()
            ?: throw IllegalStateException("${query.artist} ${query.title} 검색 결과가 없습니다.")
        return PlayMusicResult(videoUrl = "https://www.youtube.com/watch?v=$videoId")
    }
}