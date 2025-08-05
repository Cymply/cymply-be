package com.cymply.user.adapter.`in`.web.controller

import com.cymply.common.response.ApiResponse
import com.cymply.user.application.port.`in`.RegisterRecipientCodeUseCase
import com.cymply.user.application.port.`in`.GetRecipientUseCase
import com.cymply.user.adapter.`in`.web.dto.RecipientCodeResponse
import com.cymply.user.adapter.`in`.web.dto.SignupRequest
import com.cymply.user.adapter.`in`.web.dto.UserResponse
import com.cymply.user.application.port.`in`.GetUserUseCase
import com.cymply.user.application.port.`in`.RegisterUserUseCase
import com.cymply.user.application.port.`in`.ValidateNicknameUseCase
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class UserGetController(
    private val getUserUseCase: GetUserUseCase,
    private val validateNicknameUseCase: ValidateNicknameUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val registerRecipientCodeUseCase: RegisterRecipientCodeUseCase,
    private val getRecipientUseCase: GetRecipientUseCase
) : UserApiController {
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
    ): ApiResponse<String?> {
        val result = validateNicknameUseCase.validateNickname(nickname)
        if (!result) {
            return ApiResponse.failure(content = null, errorMessage = "사용할 수 없는 닉네임입니디.")
        }
        return ApiResponse.success(content = "사용 가능한 닉네임입니다.")
    }

    override fun signupOAuth2User(
        @AuthenticationPrincipal principal: Jwt,
        @RequestBody request: SignupRequest
    ): ApiResponse<String?> {
        val command = SignupRequest.from(principal, request)
        val result = registerUserUseCase.registerOAuth2User(command)
        if (result <= 0) {
            return ApiResponse.failure(content = null, errorMessage = "회원가입에 실패하였습니다. 다시 시도해주세요.")
        }
        return ApiResponse.success(content = "회원가입이 완료되었습니다.")
    }

    /**
     * TODO
     * 편지 작성 폼 URL 사용 시 재조정 필요
     */
    override fun createRecipientCode(
        @AuthenticationPrincipal principal: Jwt,
    ): ApiResponse<RecipientCodeResponse?> {
        val id = principal.getClaimAsString("id").toLong()
        val result = registerRecipientCodeUseCase.register(id)
        val response = RecipientCodeResponse(result, "https://www.cymply.kr/letter/code/${result}")
        return ApiResponse.success(response)
    }

    override fun getRecipient(
        @AuthenticationPrincipal principal: Jwt,
        code: String
    ): ApiResponse<UserResponse?> {
        val info = getRecipientUseCase.getRecipient(code)
        val response = UserResponse.from(info)
        return ApiResponse.success(response)
    }
}
