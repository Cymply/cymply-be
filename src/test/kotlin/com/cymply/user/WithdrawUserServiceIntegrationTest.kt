package com.cymply.user

import com.cymply.common.model.Gender
import com.cymply.user.adapter.out.persistence.entity.OAuth2UserEntity
import com.cymply.user.adapter.out.persistence.entity.UserEntityProfile
import com.cymply.user.adapter.out.persistence.repository.UserJpaRepository
import com.cymply.user.application.service.WithdrawUserService
import com.cymply.user.domain.User
import com.cymply.user.domain.UserProfile
import com.cymply.user.domain.UserProvider
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.util.*

@SpringBootTest
class WithdrawUserServiceIntegrationTest {

    @Autowired
    lateinit var service: WithdrawUserService

    @Autowired
    lateinit var repository: UserJpaRepository

    @Test
    @Transactional
    fun `정상정인 회원 탈퇴`() {
        // given
        val user = OAuth2UserEntity(
            role = User.Role.USER,
            email = "gildonghong@cymply.com",
            nickname = "GilDongHong",
            profile = UserEntityProfile(
                gender = Gender.M,
                ageRange = UserProfile.AgeRange.AGE_20_24
            ),
            provider = UserProvider.GOOGLE,
            sub = UUID.randomUUID().toString()
        )
        repository.save(user)

        // when
        service.withdrawActiveUser(user.id!!)

        // then
        val find = repository.findById(user.id!!).get()
        assertThat(find.deletedAt).isNotNull()
        assertThat(find.profile?.ageRange).isNotNull()
    }

}