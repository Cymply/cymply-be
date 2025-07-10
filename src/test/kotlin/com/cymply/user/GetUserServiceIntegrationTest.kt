package com.cymply.user

import com.cymply.common.model.Gender
import com.cymply.user.adapter.out.persistence.repository.UserJpaRepository
import com.cymply.user.adapter.out.persistence.entity.OAuth2UserEntity
import com.cymply.user.adapter.out.persistence.entity.UserEntityProfile
import com.cymply.user.application.port.`in`.GetUserUseCase
import com.cymply.user.domain.User
import com.cymply.user.domain.UserProvider
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.util.*

@SpringBootTest
@Transactional
class GetUserServiceIntegrationTest {
    @Autowired
    lateinit var getUserUserCase: GetUserUseCase

    @Autowired
    lateinit var userRepository: UserJpaRepository

    @BeforeEach
    fun init() {
        val entity = OAuth2UserEntity(
            role = User.Role.USER,
            provider = UserProvider.GOOGLE,
            sub = UUID.randomUUID().toString(),
            email = "gildonghong@cymply.com",
            nickname = "Gildonghong",
            profile = UserEntityProfile(
                name = "Gildonghong",
                gender = Gender.M,
                birth = LocalDate.now()
            )
        )
        userRepository.save(entity)
    }

    @Test
    fun `유효한 사용자 조회 테스트`() {
        // given
        val id = 1L

        // when
        val user = getUserUserCase.getActiveUserOrElseThrow(id)

        // then
        Assertions.assertThat(user).isNotNull
        Assertions.assertThat(user.nickname).isEqualTo("Gildonghong")
    }
}