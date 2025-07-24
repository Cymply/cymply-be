package com.cymply.letter

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.cymply.letter.application.dto.SendLetterCommand
import com.cymply.letter.application.service.SendLetterService
import com.cymply.user.adapter.out.persistence.entity.OAuth2UserEntity
import com.cymply.user.adapter.out.persistence.entity.RecipientCodeEntity
import com.cymply.user.domain.User
import com.cymply.user.domain.UserProvider
import jakarta.persistence.EntityManager
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class SendLetterServiceIntegrationTest {

    @Autowired
    lateinit var service: SendLetterService

    @Autowired
    lateinit var em: EntityManager

    @Test
    @Transactional
    fun `편지 전송 성공 테스트`() {
        // given
        val recipient = OAuth2UserEntity(
            role = User.Role.USER,
            provider = UserProvider.GOOGLE,
            sub = UUID.randomUUID().toString(),
            email = "test1@test.com",
            nickname = "test1",
            profile = null
        )
        em.persist(recipient)

        val code = RecipientCodeEntity(
            code = NanoIdUtils.randomNanoId(),
            recipientId = recipient.id!!
        )
        em.persist(code)

        val sender = OAuth2UserEntity(
            role = User.Role.USER,
            provider = UserProvider.GOOGLE,
            sub = UUID.randomUUID().toString(),
            email = "test2@test.com",
            nickname = "test2",
            profile = null
        )
        em.persist(sender)

        val command = SendLetterCommand(sender.id!!, code.code, "Hello World!", "Hello", "Adele")

        // when
        val result = service.sendLetter(command)

        // then
        Assertions.assertThat(result).isNotNull
        Assertions.assertThat(result).isGreaterThan(0)
    }
}