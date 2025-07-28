package com.cymply.user

import com.cymply.user.application.service.RegisterUserService
import com.cymply.user.application.port.`in`.RegisterOAuth2UserCommand
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class RegisterUserServiceIntegrationTest {

    @Autowired
    lateinit var service: RegisterUserService

    @Test
    fun `회원 가입 테스트`() {
        // given
        val command = RegisterOAuth2UserCommand(
            provider = "GOOGLE",
            sub = UUID.randomUUID().toString(),
            email = "test@test.com",
            nickname = "test",
            ageRange = "AGE_20_24"
        )
        // when
        val result = service.registerOAuth2User(command)

        Assertions.assertThat(result).isNotNull
        Assertions.assertThat(result).isGreaterThan(0)
    }
}