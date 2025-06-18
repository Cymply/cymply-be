package com.cymply.auth

import com.cymply.auth.application.port.out.LoadRefreshTokenPort
import com.cymply.auth.application.port.out.SaveRefreshTokenPort
import com.cymply.auth.application.service.RefreshTokenService
import com.cymply.auth.domain.AuthenticatedPrincipal
import com.cymply.common.util.JwtUtils
import com.cymply.user.application.port.`in`.GetUserUseCase
import com.cymply.user.application.service.UserSimpleInfo
import com.cymply.user.domain.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.*

class RefreshTokenServiceTest {
    private val jwtUtils = mockk<JwtUtils>()
    private val getUserUseCase = mockk<GetUserUseCase>()
    private val loadRefreshTokenPort = mockk<LoadRefreshTokenPort>()
    private val saveRefreshTokenPort = mockk<SaveRefreshTokenPort>()

    private val service = RefreshTokenService(jwtUtils, getUserUseCase, loadRefreshTokenPort, saveRefreshTokenPort)

    @Test
    fun `유효한 RefreshToken은 성공적으로 요청을 처리한다`() {
        // given
        val refreshToken = UUID.randomUUID().toString()
        val newRefreshToken = UUID.randomUUID().toString()
        val newAccessToken = UUID.randomUUID().toString()

        val user = UserSimpleInfo(1, "test@test.com", "test", User.Role.USER)
        val principal = AuthenticatedPrincipal.of(user.id, user.email, user.nickname, user.role.name)

        every { loadRefreshTokenPort.loadRefreshToken(refreshToken) } returns refreshToken
        every { jwtUtils.extractUserId(refreshToken) } returns user.id
        every { getUserUseCase.getActiveUserOrElseThrow(user.id) } returns user
        every { jwtUtils.generate(principal.getAttributes(), 1800000) } returns newRefreshToken
        every { jwtUtils.generate(principal.getAttributes(), 604800000) } returns newAccessToken
        every { saveRefreshTokenPort.saveRefreshToken(any(), any()) } returns Unit

        // when
        val result = service.refreshToken(refreshToken)
    }

    @Test
    fun `만료된 RefreshToken은 IllegalArgumentException을 반환한다`() {
        // given
        val refreshToken = UUID.randomUUID().toString()
        every { loadRefreshTokenPort.loadRefreshToken(refreshToken) } returns null
        // when, then
        assertThrows<IllegalArgumentException> { service.refreshToken(refreshToken) }
    }


    @Test
    fun `유효하지 않은 회원의 RefreshToken은 IllegalArgumentException을 반환한다`() {
        // given
        val refreshToken = UUID.randomUUID().toString()
        val user = UserSimpleInfo(1, "test@test.com", "test", User.Role.USER)

        every { loadRefreshTokenPort.loadRefreshToken(refreshToken) } returns refreshToken
        every { jwtUtils.extractUserId(refreshToken) } returns user.id
        every { getUserUseCase.getActiveUserOrElseThrow(user.id) }.throws(IllegalArgumentException("User not found"))

        // when, then
        assertThrows<IllegalArgumentException> { service.refreshToken(refreshToken) }
    }
}