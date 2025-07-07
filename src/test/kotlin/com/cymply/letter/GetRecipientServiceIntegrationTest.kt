package com.cymply.letter

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.cymply.letter.adapter.out.persistence.entity.LetterCodeEntity
import com.cymply.letter.application.service.GetRecipientService
import com.cymply.user.adapter.out.persistence.entity.OAuth2UserEntity
import com.cymply.user.domain.User
import com.cymply.user.domain.UserProvider
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GetRecipientServiceIntegrationTest {
    @Autowired
    lateinit var service: GetRecipientService

    @Autowired
    lateinit var em: EntityManager

    @Test
    @Transactional
    fun `유효가 편지 코드인 경우 수신자의 정보를 조회한다`() {
        // given
        val user = OAuth2UserEntity(
            role = User.Role.USER,
            email = "test@test.com",
            nickname = "test",
            profile = null,
            sub = "test",
            provider = UserProvider.KAKAO,
        )

        val code = LetterCodeEntity(
            code = NanoIdUtils.randomNanoId(),
            recipientId = 1L,
            expiredAt = null
        )

        em.persist(user)
        em.persist(code)

        em.clear()
        em.flush()

        // when
        val recipient = service.getRecipient(code.code)

        // then
        assertThat(recipient.email).isEqualTo(user.email)
        assertThat(recipient.nickname).isEqualTo(user.nickname)
    }
}