package com.cymply.music.adapter.out.youtube.config

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class YouTubeConfig {

    @Bean
    fun youTube(): YouTube {
        return YouTube.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            JacksonFactory.getDefaultInstance(),
            null
        )
            .setApplicationName("cymply-video-server")
            .build()
    }
}