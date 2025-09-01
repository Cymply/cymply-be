package com.cymply.letter.application.dto

import com.cymply.letter.domain.Letter
import com.cymply.music.application.port.dto.GetMusicResult
import com.cymply.user.domain.RecipientCode

data class GetLetterResult(
    val id: Long,
    val senderId: Long,
    val title: String,
    val content: String,
    val musicTitle: String,
    val musicArtist: String,
    val musicThumbnailUrl: String,
    val musicVideoUrl: String,
    val recipientCode: String? = null,
) {
    companion object {
        fun toResult(letter: Letter, music: GetMusicResult, recipientCode: RecipientCode?): GetLetterResult =
            GetLetterResult(
                id = letter.id!!,
                senderId = letter.senderId,
                title = letter.title,
                content = letter.content,
                musicTitle = music.title,
                musicArtist = music.artist,
                musicThumbnailUrl = music.thumbnailUrl,
                musicVideoUrl = music.videoUrl,
                recipientCode = recipientCode?.code,
            )
    }
}
