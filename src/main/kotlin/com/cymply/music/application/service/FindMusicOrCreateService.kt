package com.cymply.music.application.service

import com.cymply.music.application.port.dto.MusicSearchRequest
import com.cymply.music.application.port.dto.MusicSimpleInfo
import com.cymply.music.application.port.dto.PlayMusicQuery
import com.cymply.music.application.port.`in`.FindMusicOrCreateUseCase
import com.cymply.music.application.port.out.LoadMusicPort
import com.cymply.music.application.port.out.SaveMusicPort
import com.cymply.music.application.port.out.SearchMusicPort
import com.cymply.music.application.port.out.YouTubeVideoSearchPort
import com.cymply.music.domain.model.Music
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

/**
 * 타이틀과 아티스트명 조회 후 없는 경우 저장
 */
@Service
class FindMusicOrCreateService(
    private val loadMusicPort: LoadMusicPort,
    private val searchMusicPort: SearchMusicPort,
    private val youTubePort: YouTubeVideoSearchPort,
    private val saveMusicPort: SaveMusicPort
) : FindMusicOrCreateUseCase {

    companion object {
        const val YOUTUBE_BASE_URL = "https://www.youtube.com/watch?v="
    }

    @Transactional
    override fun findMusicOrCreate(query: PlayMusicQuery): MusicSimpleInfo {
        val find = loadMusicPort.loadByTitleAndArtist(query.title, query.artist)

        if (find != null) {
            return MusicSimpleInfo.from(find)
        }

        val results = searchMusicPort.search(MusicSearchRequest("${query.title} ${query.artist}", 30, 1))
        val result = results.find { it.title == query.title && it.artist == query.artist }

        if (result == null) {
            throw IllegalArgumentException("음악이 존재하지 않습니다.")
        }

        val videoId = youTubePort.searchVideoIds("${query.artist} ${query.title} official audio").first()
        val videoUrl = "$YOUTUBE_BASE_URL$videoId"
        print("videoUrl: $videoUrl")

        val music = Music.of(result.title, result.artist, result.artist, result.thumbnailUrl, videoUrl)
        val saved = saveMusicPort.save(music)

        return MusicSimpleInfo.from(saved)
    }

}