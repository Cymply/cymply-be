package com.cymply.auth

import com.cymply.auth.adapter.out.redis.RefreshTokenRedisAdapter
import com.cymply.auth.application.service.ExpireTokenService
import com.cymply.auth.domain.AuthenticatedPrincipal
import com.cymply.common.util.JwtUtils
import com.cymply.user.adapter.out.persistence.repository.OAuth2UserJpaRepository
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
class ExpireTokenIntegrationTest {

    @Autowired
    private lateinit var expireTokenService: ExpireTokenService

    @Autowired
    lateinit var service: ExpireTokenService

    @Autowired
    lateinit var jwtUtils: JwtUtils

    @Autowired
    lateinit var refreshTokenAdapter: RefreshTokenRedisAdapter

    @Autowired
    lateinit var userJpaRepository: OAuth2UserJpaRepository

    @Test
    @Transactional
    fun `RefreshToken이 유효한 경우 해당 값을 삭제한다`() {
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
        expireTokenService.expireToken(refreshToken)

        // then
        val exist = refreshTokenAdapter.loadRefreshToken(jwtUtils.getId(refreshToken))
        Assertions.assertThat(exist).isNull()
    }
}