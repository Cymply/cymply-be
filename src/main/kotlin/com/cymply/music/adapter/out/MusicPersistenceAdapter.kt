package com.cymply.music.adapter.out

import com.cymply.music.adapter.out.repository.MusicJpaRepository
import com.cymply.music.application.port.out.LoadMusicPort
import com.cymply.music.application.port.out.SaveMusicPort
import com.cymply.music.domain.model.Music
import org.springframework.stereotype.Repository

@Repository
class MusicPersistenceAdapter(
    private val mapper: MusicEntityMapper,
    private val repository: MusicJpaRepository,
) : LoadMusicPort, SaveMusicPort {

    override fun loadById(id: Long): Music {
        val entity = repository.findById(id).orElseThrow {
            throw IllegalArgumentException("음악 없음")
        }
        return mapper.from(entity)
    }

    override fun loadByTitleAndArtist(title: String, artist: String): Music? {
        val entity = repository.findByTitleAndArtist(title, artist)

        if (entity != null) {
            return mapper.from(entity)
        }
        return null
    }


    override fun save(music: Music): Music {
        val entity = repository.save(mapper.from(music))
        return mapper.from(entity)
    }
}