package com.cymply.letter

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.cymply.user.application.port.`in`.GetRecipientUseCase
import com.cymply.letter.application.dto.SetLetterNicknameCommand
import com.cymply.letter.application.port.out.LoadLetterNicknamePort
import com.cymply.letter.application.port.out.SaveLetterNicknamePort
import com.cymply.letter.application.service.SetLetterNicknameService
import com.cymply.user.domain.RecipientCode
import com.cymply.letter.domain.LetterNickname
import com.cymply.user.application.service.UserSimpleInfo
import com.cymply.user.domain.User
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class SetNicknameServiceUnitTest {

    private val getRecipientUseCase = mockk<GetRecipientUseCase>()
    private val loadLetterNicknamePort = mockk<LoadLetterNicknamePort>()
    private val saveLetterNicknamePort = mockk<SaveLetterNicknamePort>()

    private var service = SetLetterNicknameService(getRecipientUseCase, loadLetterNicknamePort, saveLetterNicknamePort)

    @Test
    fun `닉네임 설정 성공`() {
        // given
        val recipient = UserSimpleInfo(
            1L,
            "test1@test.com",
            "test1",
            User.Role.USER,
        )

        val sender = UserSimpleInfo(
            2L,
            "test2@test.com",
            "test2",
            User.Role.USER
        )

        val code = RecipientCode(
            1L,
            NanoIdUtils.randomNanoId(),
            recipient.id,
            LocalDateTime.now()
        )

        every { getRecipientUseCase.getRecipient(code.code) }.returns(recipient)
        every { loadLetterNicknamePort.load(sender.id, recipient.id) }.returns(null)
        every { saveLetterNicknamePort.save(any()) }.returns(1L)

        val command = SetLetterNicknameCommand(sender.id, code.code, "test")

        // when
        service.setLetterNickname(command)

        // then
        verify { saveLetterNicknamePort.save(any()) }
    }

    @Test
    fun `이미 닉네임이 설정된 경우 Exception을 반환한다`() {
        // given
        val recipient = UserSimpleInfo(
            1L,
            "test1@test.com",
            "test1",
            User.Role.USER,
        )

        val sender = UserSimpleInfo(
            2L,
            "test2@test.com",
            "test2",
            User.Role.USER
        )

        val code = RecipientCode(
            1L,
            NanoIdUtils.randomNanoId(),
            recipient.id,
            LocalDateTime.now()
        )

        val nickname = LetterNickname(
            1L,
            recipient.id,
            "test2-old",
            LocalDateTime.now()
        )

        every { getRecipientUseCase.getRecipient(code.code) }.returns(recipient)
        every { loadLetterNicknamePort.load(sender.id, recipient.id) }.returns(nickname)
        every { saveLetterNicknamePort.save(any()) }.returns(1L)

        val command = SetLetterNicknameCommand(sender.id, code.code, "test2-new")

        // when, then
        Assertions.assertThatThrownBy { service.setLetterNickname(command) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }
}