package com.cymply.music.adapter.out.repository

import com.cymply.music.adapter.out.entity.MusicEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MusicJpaRepository : JpaRepository<MusicEntity, Long> {
    fun findByTitleAndArtist(title: String, artist: String): MusicEntity?
}