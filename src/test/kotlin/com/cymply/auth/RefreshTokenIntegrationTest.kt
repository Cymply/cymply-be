package com.cymply.auth

import com.cymply.auth.adapter.out.redis.RefreshRefreshTokenRedisAdapter
import com.cymply.auth.application.service.RefreshTokenService
import com.cymply.auth.domain.AuthenticatedPrincipal
import com.cymply.common.util.JwtUtils
import com.cymply.user.adapter.out.persistence.OAuth2UserJpaRepository
import com.cymply.user.adapter.out.persistence.entity.OAuth2UserEntity
import com.cymply.user.domain.User
import com.cymply.user.domain.UserProvider
import jakarta.transaction.Transactional
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID


@SpringBootTest
class RefreshTokenIntegrationTest {

    @Autowired
    lateinit var refreshTokenService: RefreshTokenService

    @Autowired
    lateinit var jwtUtils: JwtUtils

    @Autowired
    lateinit var refreshTokenAdapter: RefreshRefreshTokenRedisAdapter

    @Autowired
    lateinit var userJpaRepository: OAuth2UserJpaRepository

    @Test
    @Transactional
    fun `유효한 RefreshToken은 성공적으로 요청을 처리한다`() {
        // given
        val user = OAuth2UserEntity(
            id = null,
            role = User.Role.USER,
            "test@test.com",
            "test",
            null,
            UUID.randomUUID().toString(),
            UserProvider.GOOGLE,
        )
        userJpaRepository.save(user)

        val principal = AuthenticatedPrincipal.of(user.id!!, user.email, user.nickname, user.role.name)
        val refreshToken = jwtUtils.generate(principal.getAttributes(), 10000)
        refreshTokenAdapter.saveRefreshToken(jwtUtils.getId(refreshToken), refreshToken, 10000)

        // when
        val result = refreshTokenService.refreshToken(refreshToken)

        // then
        Assertions.assertThat(result.accessToken).isNotNull()
        Assertions.assertThat(result.refreshToken).isNotNull()
    }
}