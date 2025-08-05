package com.cymply.music

import com.cymply.music.application.port.dto.PlayMusicQuery
import com.cymply.music.application.service.FindMusicOrCreateService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class FindMusicOrCreateServiceIntegrationTest {

    @Autowired
    lateinit var service: FindMusicOrCreateService

    @Test
    fun `영문 음악 아티스트 검색 및 저장`() {
        val query = PlayMusicQuery("Hello", "Adele")
        val result = service.findMusicOrCreate(query)
        println(result)
    }

    @Test
    fun `한국 음악 아티스트 검색 및 저장`() {
        val query = PlayMusicQuery("좋은 날", "아이유")
        val result = service.findMusicOrCreate(query)
        println(result)
    }
}