package com.cymply.common

import com.cymply.common.util.JwtUtil
import io.jsonwebtoken.ExpiredJwtException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JwtUtilTest(
    @Autowired val jwtUtil: JwtUtil
) {
    @Test
    fun `토큰 발급`() {
        // given
        val username = "google-1"
        val role = "ROLE_USER"
        // when
        val token = jwtUtil.generate(username, role, 10000)

        // then
        Assertions.assertEquals(jwtUtil.getUsername(token), username)
        Assertions.assertEquals(jwtUtil.getRole(token), role)
        Assertions.assertTrue(jwtUtil.isExpired(token))
    }

    @Test
    fun `만료된 토큰은 ExpiredJwtException 예외 발생`() {
        // given
        val username = "google-1"
        val role = "ROLE_USER"
        val token = jwtUtil.generate(username, role, 100)
        Thread.sleep(100)

        // when, then
        Assertions.assertThrows(ExpiredJwtException::class.java) {
            jwtUtil.isExpired(token)
        }

    }
}