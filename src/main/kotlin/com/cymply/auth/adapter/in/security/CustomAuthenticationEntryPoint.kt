package com.cymply.auth.adapter.`in`.security

import com.cymply.common.exception.TokenExpiredException
import com.cymply.common.response.ApiResponse
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json;charset=UTF-8"

        val message = when (authException.cause) {
            is TokenExpiredException -> "AccessToken이 만료되었습니다."
            else -> "인증이 필요합니다."
        }

        val body = ApiResponse.failure(content = null, errorMessage = message)
        response.writer.write(objectMapper.writeValueAsString(body))
    }
}
