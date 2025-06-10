package com.cymply.auth.adapter.`in`.security

import com.cymply.common.response.ApiResponse
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

/**
 * 인증되지 않은 사용자가 접근하는 경우 예외 처리
 */
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
        val message = "유효하지 않은 토큰입니다."
        val body = ApiResponse.failure(content = null, errorMessage = message)
        response.writer.write(objectMapper.writeValueAsString(body))
    }
}
