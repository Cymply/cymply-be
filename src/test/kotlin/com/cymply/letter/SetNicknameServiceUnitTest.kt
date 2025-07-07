package com.cymply.letter

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.cymply.letter.application.port.`in`.SetNicknameCommand
import com.cymply.letter.application.port.out.LoadLetterCodePort
import com.cymply.letter.application.port.out.LoadLetterNicknamePort
import com.cymply.letter.application.port.out.SaveLetterNicknamePort
import com.cymply.letter.application.service.SetNicknameService
import com.cymply.letter.domain.LetterCode
import com.cymply.letter.domain.LetterNickname
import com.cymply.user.domain.OAuth2User
import com.cymply.user.domain.User
import com.cymply.user.domain.UserProvider
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class SetNicknameServiceUnitTest {

    private val loadLetterCodePort = mockk<LoadLetterCodePort>()
    private val loadLetterNicknamePort = mockk<LoadLetterNicknamePort>()
    private val saveLetterNicknamePort = mockk<SaveLetterNicknamePort>()

    private var service = SetNicknameService(loadLetterCodePort, loadLetterNicknamePort, saveLetterNicknamePort)

    @Test
    fun `닉네임 설정 성공`() {
        // given
        val recipient = OAuth2User(
            1L,
            "test1@test.com",
            "test1",
            User.Role.USER,
            null,
            null,
            UserProvider.GOOGLE,
            NanoIdUtils.randomNanoId()
        )

        val sender = OAuth2User(
            2L,
            "test2@test.com",
            "test2",
            User.Role.USER,
            null,
            null,
            UserProvider.KAKAO,
            NanoIdUtils.randomNanoId()
        )

        val code = LetterCode(
            1L,
            recipient.getIdOrThrow(),
            NanoIdUtils.randomNanoId(),
            LocalDateTime.now()
        )

        every { loadLetterCodePort.loadLetterCode(code.code) }.returns(code)
        every { loadLetterNicknamePort.loadLetterNickname(sender.id!!, recipient.id!!) }.returns(null)
        every { saveLetterNicknamePort.saveLetterNickname(any()) }.returns(1L)
        val command = SetNicknameCommand(sender.getIdOrThrow(), code.code, "test")

        // when
        service.setNickname(command)
        verify { saveLetterNicknamePort.saveLetterNickname(any()) }
    }

    @Test
    fun `이미 닉네임이 설정된 경우 Exception을 반환한다`() {
        // given
        val sender = OAuth2User(
            2L,
            "test2@test.com",
            "test2",
            User.Role.USER,
            null,
            null,
            UserProvider.KAKAO,
            NanoIdUtils.randomNanoId()
        )

        val recipient = OAuth2User(
            1L,
            "test1@test.com",
            "test1",
            User.Role.USER,
            null,
            null,
            UserProvider.GOOGLE,
            NanoIdUtils.randomNanoId()
        )

        val code = LetterCode(
            1L,
            recipient.getIdOrThrow(),
            NanoIdUtils.randomNanoId(),
            LocalDateTime.now()
        )

        val nickname = LetterNickname(sender.id!!, recipient.id!!, "test2", LocalDateTime.now())

        every { loadLetterCodePort.loadLetterCode(code.code) }.returns(code)
        every { loadLetterNicknamePort.loadLetterNickname(sender.id!!, recipient.id!!) }.returns(nickname)
        every { saveLetterNicknamePort.saveLetterNickname(any()) }.returns(1L)
        val command = SetNicknameCommand(sender.getIdOrThrow(), code.code, "test")

        // when, then
        Assertions.assertThatThrownBy { service.setNickname(command) }
            .isInstanceOf(IllegalArgumentException::class.java)
    }
}