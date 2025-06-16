package com.cymply.auth

import com.cymply.auth.adapter.`in`.security.OAuth2LoginRequest
import org.junit.jupiter.api.Test

class OAuth2LoginRequestTest {

    @Test
    fun `oauth2 로그인 요청 format 테스트`() {
        val attributes = mapOf(
            "id" to 123456789,
            "kakao_account" to mapOf(
                "email" to "sample@sample.com",
                "name" to "홍길동",
                "gender" to "female",
                "birthyear" to "2002",
                "birthday" to "1130"
            )

        )
        val registration = "kakao"

        val request = OAuth2LoginRequest(attributes, registration)
        val command = request.of()
        println(command)
    }
}