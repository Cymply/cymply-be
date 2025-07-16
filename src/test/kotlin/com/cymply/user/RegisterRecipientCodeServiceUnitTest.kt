package com.cymply.user

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.cymply.user.application.port.out.LoadRecipientCodePort
import com.cymply.user.application.port.out.SaveRecipientCodePort
import com.cymply.user.application.service.RegisterRecipientCodeService
import com.cymply.user.domain.RecipientCode
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class RegisterRecipientCodeServiceUnitTest {
    private val loadRecipientCodePort = mockk<LoadRecipientCodePort>()
    private val saveRecipientCodePort = mockk<SaveRecipientCodePort>()

    private val service = RegisterRecipientCodeService(loadRecipientCodePort, saveRecipientCodePort)

    @Test
    fun `초대 코드가 존재하지 않는 경우 초대 코드를 생성한다`() {
        // given
        every { loadRecipientCodePort.load(1L) } returns null
        every { saveRecipientCodePort.save(any()) } returns 1L

        // when
        val result = service.register(1L)

        // then
        assertThat(result).hasSizeGreaterThanOrEqualTo(1)
        verify { saveRecipientCodePort.save(any()) }
    }

    @Test
    fun `초대 코드가 존재하는 경우 기존 초대 코드를 반환한다`() {
        // given
        val code = RecipientCode(1L, NanoIdUtils.randomNanoId(), 1L, LocalDateTime.now())
        every { loadRecipientCodePort.load(1L) } returns code

        // when
        val result = service.register(1L)

        // then
        assertThat(result).isEqualTo(code.code)
    }
}