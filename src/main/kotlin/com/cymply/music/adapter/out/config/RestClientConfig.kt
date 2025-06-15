package com.cymply.music.adapter.out.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class RestClientConfig {

    @Bean
    fun lastFmRestClient(): RestClient =
        RestClient.builder()
            .baseUrl("https://ws.audioscrobbler.com")
            .build()
}