package com.cymply.music.adapter.out.config

import com.cymply.music.adapter.out.lastfm.client.LastFmApiClient
import com.cymply.music.adapter.out.spotify.client.SpotifyApiClient
import com.cymply.music.adapter.out.spotify.client.SpotifyAuthClient
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.client.RestClient
import org.springframework.web.client.support.RestClientAdapter
import org.springframework.web.service.invoker.HttpServiceProxyFactory

@Configuration
class RestClientConfig(
    @Value("\${spotify.auth.base-url}")
    private val spotifyAuthBaseUrl: String,
    @Value("\${spotify.api.base-url}")
    private val spotifyApiBaseUrl: String,
    @Value("\${lastfm.api.base-url}")
    private val lastFmApiBaseUrl: String
) {

    private val logger = LoggerFactory.getLogger(RestClientConfig::class.java)

    @Bean
    fun lastFmApiClient(): LastFmApiClient {
        val restClient = RestClient.builder().baseUrl(lastFmApiBaseUrl).build()
        val adapter = RestClientAdapter.create(restClient)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()

        return factory.createClient(LastFmApiClient::class.java)
    }

    // 프록시 팩토리 컴포넌트 만들어서 중복 코드 제거하기

    @Bean
    fun spotifyAuthClient(): SpotifyAuthClient {
        val restClient = RestClient.builder()
            .baseUrl(spotifyAuthBaseUrl)
            .requestInterceptor { request, body, execution ->
                logger.info("${request.method} ${request.uri}")
                val response = execution.execute(request, body)
                logger.info("${response.statusCode}")
                response
            }
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
            .build()
        val adapter = RestClientAdapter.create(restClient)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()

        return factory.createClient(SpotifyAuthClient::class.java)
    }

    @Bean
    fun spotifyApiClient(): SpotifyApiClient {
        val restClient = RestClient.builder()
            .baseUrl(spotifyApiBaseUrl)
            .requestInterceptor { request, body, execution ->
                logger.info("${request.method} ${request.uri}")
                val response = execution.execute(request, body)
                logger.info("${response.statusCode}")
                response
            }
            .build()
        val adapter = RestClientAdapter.create(restClient)
        val factory = HttpServiceProxyFactory.builderFor(adapter).build()

        return factory.createClient(SpotifyApiClient::class.java)
    }
}