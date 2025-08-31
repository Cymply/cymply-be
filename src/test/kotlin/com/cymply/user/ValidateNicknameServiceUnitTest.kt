package com.cymply.user

import com.cymply.user.application.port.out.LoadUserPort
import com.cymply.user.application.service.ValidateNicknameService
import com.cymply.user.domain.OAuth2User
import com.cymply.user.domain.User
import com.cymply.user.domain.UserProfile
import com.cymply.user.domain.UserProvider
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.util.*

class ValidateNicknameServiceUnitTest {
    private val loadUserPort = mockk<LoadUserPort>()
    private val service = ValidateNicknameService(loadUserPort)

    @Test
    fun `사용 가능한 닉네임은 true 반환`() {
        // given
        val nickname = "길dong!#"
        every { loadUserPort.loadUserByNickname(nickname) } returns null

        // when
        val result = service.validateNickname(nickname)
        // then
        Assertions.assertThat(result).isTrue()
    }

    @Test
    fun `길이가 11인 닉네임은 false 반환`() {
        // given
        val nickname = "honggildong"
        every { loadUserPort.loadUserByNickname(nickname) } returns null

        // when
        val result = service.validateNickname(nickname)
        // then
        Assertions.assertThat(result).isFalse
    }

    @Test
    fun `숫자를 포함하는 닉네임은 false 반환`() {
        // given
        val nickname = "gi1dong"
        every { loadUserPort.loadUserByNickname(nickname) } returns null

        // when
        val result = service.validateNickname(nickname)
        // then
        Assertions.assertThat(result).isFalse
    }

    @Test
    fun `허용되지 않은 특수문자 @를 포함하는 닉네임은 false 반환`() {
        // given
        val nickname = "gil@dong"
        every { loadUserPort.loadUserByNickname(nickname) } returns null

        // when
        val result = service.validateNickname(nickname)
        // then
        Assertions.assertThat(result).isFalse
    }


    @Test
    fun `사용중인 닉네임은 false 반환`() {
        // given
        val nickname = "길dong!#"
        val user = OAuth2User(
            role = User.Role.USER,
            nickname = nickname,
            email = "gildong@cymply.com",
            profile = UserProfile(null, null),
            sub = UUID.randomUUID().toString(),
            provider = UserProvider.GOOGLE
        )
        every { loadUserPort.loadUserByNickname(nickname) } returns user

        // when
        val result = service.validateNickname(nickname)
        // then
        Assertions.assertThat(result).isFalse
    }
}