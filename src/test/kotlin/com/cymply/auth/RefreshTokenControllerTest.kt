package com.cymply.auth

import com.cymply.auth.adapter.`in`.web.AuthController
import com.cymply.auth.application.port.`in`.RefreshTokenUseCase
import com.cymply.auth.application.port.`in`.ReissueTokenUseCase
import com.cymply.common.util.JwtUtils
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@WebMvcTest(AuthController::class)
@MockitoBean(types = [ReissueTokenUseCase::class, RefreshTokenUseCase::class])
class RefreshTokenControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `인가된 사용자는 성공적으로 요청을 처리한다`() {
        // given
        val jwt = Jwt.withTokenValue("token")
            .header("alg", "none")
            .claim("id", 1)
            .claim("email", "test@test.com")
            .claim("scope", "user")
            .build()

        val authorities = AuthorityUtils.createAuthorityList("SCOPE_user")
        val token = JwtAuthenticationToken(jwt, authorities)

        // when
        mockMvc.perform(
            post("/api/v1/auth/token/refresh")
                .with(authentication(token))
        ).andExpect(status().isOk());
    }

}

