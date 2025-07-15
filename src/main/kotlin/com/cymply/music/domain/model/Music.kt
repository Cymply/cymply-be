package com.cymply.music.domain.model

class Music(
    val id: Long? = null,
    val title: String,
    val artist: String,
    val album: String?,
    val thumbnailUrl: String,
    val videoUrl: String? = null,
) {

    companion object {
        fun of(title: String, artist: String, album: String, thumbnailUrl: String, videoUrl: String): Music {
            return Music(
                title = title,
                artist = artist,
                album = album,
                thumbnailUrl = thumbnailUrl,
                videoUrl = videoUrl
            )
        }
    }

    fun getIdOrThrow() = id ?: throw IllegalStateException("Music id is null.")
}
