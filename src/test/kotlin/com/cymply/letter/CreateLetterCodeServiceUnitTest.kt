package com.cymply.letter

import com.cymply.letter.application.port.out.LoadLetterCodePort
import com.cymply.letter.application.port.out.SaveLetterCodePort
import com.cymply.letter.application.service.CreateLetterCodeService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class CreateLetterCodeServiceUnitTest {
    private val loadLetterCodePort = mockk<LoadLetterCodePort>()
    private val saveLetterCodePort = mockk<SaveLetterCodePort>()

    private val service = CreateLetterCodeService(loadLetterCodePort, saveLetterCodePort)

    @Test
    fun `초대 코드가 존재하지 않는 경우 초대 코드를 생성한다`() {
        // given
        val recipientId = 1L
        every { loadLetterCodePort.loadLetterCode(recipientId) } returns null
        every { saveLetterCodePort.saveLetterCode(any()) } returns 1L

        // when
        val result = service.createLetterCode(recipientId)

        // then
        Assertions.assertThat(result).isNotNull
        verify { saveLetterCodePort.saveLetterCode(any()) }
    }
}