package com.cymply.music.adapter.out

import com.cymply.music.adapter.out.entity.MusicEntity
import com.cymply.music.domain.model.Music
import org.springframework.stereotype.Component

@Component
class MusicEntityMapper {
    fun from(music: Music): MusicEntity {
        return MusicEntity(
            id = music.id,
            title = music.title,
            artist = music.artist,
            album = music.album,
            thumbnailUrl = music.thumbnailUrl,
            videoUrl = music.videoUrl!!,
        )
    }

    fun from(music: MusicEntity): Music {
        return Music(
            id = music.id,
            title = music.title,
            artist = music.artist,
            album = music.album,
            thumbnailUrl = music.thumbnail,
            videoUrl = music.videoUrl
        )
    }
}