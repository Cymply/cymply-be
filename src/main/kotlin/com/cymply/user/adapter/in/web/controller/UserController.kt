package com.cymply.user.adapter.`in`.web.controller

import com.cymply.common.response.ApiResponse
import com.cymply.user.adapter.`in`.web.dto.SignupRequest
import com.cymply.user.adapter.`in`.web.dto.SignupResponse
import com.cymply.user.adapter.`in`.web.dto.UserResponse
import com.cymply.user.application.port.`in`.GetUserUseCase
import com.cymply.user.application.port.`in`.RegisterUserUseCase
import com.cymply.user.application.port.`in`.ValidateNicknameUseCase
import jakarta.annotation.security.RolesAllowed
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserGetController(
    private val getUserUseCase: GetUserUseCase,
    private val validateNicknameUseCase: ValidateNicknameUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
) : UserApiController {
    @RolesAllowed("USER")
    override fun getMyAccount(
        @AuthenticationPrincipal principal: Jwt,
    ): ApiResponse<UserResponse> {
        val id = principal.getClaimAsString("id").toLong()
        val info = getUserUseCase.getActiveUserOrElseThrow(id)
        val response = UserResponse.from(info)
        return ApiResponse(true, ApiResponse.DataWrapper(content = response), null)
    }

    override fun checkAvailableNickname(
        @AuthenticationPrincipal principal: Jwt,
        nickname: String
    ): ApiResponse<Boolean?> {
        val result = validateNicknameUseCase.validateNickname(nickname)
        if (!result) {
            return ApiResponse.failure(content = null, errorMessage = "사용할 수 없는 닉네임입니디.")
        }
        return ApiResponse.success(content = null)
    }

    @RolesAllowed("PENDING_USER")
    override fun signupOAuth2User(
        @AuthenticationPrincipal principal: Jwt,
        @RequestBody request: SignupRequest
    ): ApiResponse<SignupResponse?> {
        val command = SignupRequest.from(principal, request)
        val result = registerUserUseCase.registerOAuth2User(command)
        if (result <= 0) {
            return ApiResponse.failure(content = null, errorMessage = "회원가입에 실패하였습니다. 다시 시도해주세요.")
        }
        return ApiResponse.success(content = SignupResponse("accessToken", "refreshToken"))
    }
}
