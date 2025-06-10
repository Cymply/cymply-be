package com.cymply.user.adapter.`in`.web.controller

import com.cymply.auth.adapter.`in`.security.PrincipalDetail
import com.cymply.common.response.ApiResponse
import com.cymply.user.adapter.`in`.web.dto.UserResponse
import com.cymply.user.application.port.`in`.GetUserUseCase

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserGetController(
    private val getUserUseCase: GetUserUseCase,
) : UserApiController {
    override fun findMyAccount(principal: PrincipalDetail): ApiResponse<UserResponse> {
        val id = principal.username.toLong()
        val info = getUserUseCase.getActiveUserOrElseThrow(id)
        val response = UserResponse.from(info)
        return ApiResponse(true, ApiResponse.DataWrapper(content = response), null)
    }

    override fun checkAvailableNickname(principal: PrincipalDetail, nickname: String): ApiResponse<Void> {
        TODO("Not yet implemented")
    }

    override fun signupOAuth2User(principal: PrincipalDetail): ApiResponse<Void> {
        TODO("Not yet implemented")
    }
}
