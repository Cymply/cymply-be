package com.cymply.letter

import com.aventrix.jnanoid.jnanoid.NanoIdUtils
import com.cymply.letter.adapter.out.persistence.entity.LetterNicknameEntity
import com.cymply.letter.application.service.GetLetterNicknameService
import com.cymply.user.adapter.out.persistence.entity.RecipientCodeEntity
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
class GetLetterNicknameServiceIntegrationTest {
    @Autowired
    lateinit var service: GetLetterNicknameService

    @Autowired
    lateinit var em: EntityManager

    @Test
    @Transactional
    fun `송신자 닉네임 조회 성공`() {
        // given
        val sender = OAuth2UserEntity(
            role = User.Role.USER,
            email = "test1@test.com",
            nickname = "test1",
            profile = null,
            sub = "test1",
            provider = UserProvider.KAKAO,
        )

        val recipient = OAuth2UserEntity(
            role = User.Role.USER,
            email = "test2@test.com",
            nickname = "test2",
            profile = null,
            sub = "test2",
            provider = UserProvider.KAKAO,
        )

        em.persist(sender)
        em.persist(recipient)

        val code = RecipientCodeEntity(
            code = NanoIdUtils.randomNanoId(),
            recipientId = recipient.id!!
        )

        val nickname = LetterNicknameEntity(
            senderId = sender.id!!,
            recipientId = recipient.id!!,
            nickname = "test3"
        )

        em.persist(code)
        em.persist(nickname)

        em.clear()
        em.flush()

        // when
        val result = service.getLetterNickname(sender.id!!, code.code)

        // then
        assertThat(result.nickname).isEqualTo(nickname.nickname)
        println("result.nickname ${result.nickname}")
        println("result.createdAt ${result.createdAt}")
    }
}