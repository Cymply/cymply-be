package com.cymply.user.adapter.`in`.web.controller

import com.cymply.auth.adapter.`in`.security.PrincipalDetail
import com.cymply.common.response.ApiResponse
import com.cymply.user.adapter.`in`.web.dto.UserResponse
import com.cymply.user.application.port.`in`.GetUserUseCase
import com.cymply.user.application.port.`in`.ValidateNicknameUseCase
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings
import org.springframework.security.core.annotation.AuthenticationPrincipal

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserGetController(
    private val getUserUseCase: GetUserUseCase,
    private val validateNicknameUseCase: ValidateNicknameUseCase,
) : UserApiController {
    override fun findMyAccount(
        @AuthenticationPrincipal principal: PrincipalDetail
    ): ApiResponse<UserResponse> {
        val id = principal.username.toLong()
        val info = getUserUseCase.getActiveUserOrElseThrow(id)
        val response = UserResponse.from(info)
        return ApiResponse(true, ApiResponse.DataWrapper(content = response), null)
    }

    override fun checkAvailableNickname(
        @AuthenticationPrincipal principal: PrincipalDetail,
        nickname: String
    ): ApiResponse<Boolean?> {
        val result = validateNicknameUseCase.validateNickname(nickname)
        if (!result) {
            return ApiResponse.failure(content = null, errorMessage = "사용할 수 없는 닉네임입니디.")
        }
        return ApiResponse.success(content = null)
    }

    override fun signupOAuth2User(principal: PrincipalDetail): ApiResponse<Unit> {
        TODO("Not yet implemented")
    }
}
