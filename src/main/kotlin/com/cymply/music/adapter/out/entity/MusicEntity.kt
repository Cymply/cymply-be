package com.cymply.music.adapter.out.entity

import com.cymply.common.model.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "music")
class MusicEntity(
    id: Long? = null,
    title: String,
    artist: String,
    album: String?,
    thumbnailUrl: String,
    videoUrl: String,
) : BaseTimeEntity() {

    @Id
    @GeneratedValue
    var id: Long? = id
        protected set

    @Column(name = "title", nullable = false)
    var title: String = title
        protected set

    @Column(name = "artist", nullable = false)
    var artist: String = artist
        protected set

    @Column(name = "album")
    var album: String? = album
        protected set

    @Column(name = "thumbnail_url", nullable = false)
    var thumbnail: String = thumbnailUrl
        protected set

    @Column(name = "playUrl", nullable = false)
    var videoUrl: String = videoUrl
        protected set
}