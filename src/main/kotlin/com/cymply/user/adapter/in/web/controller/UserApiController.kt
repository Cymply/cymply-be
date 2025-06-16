package com.cymply.user.adapter.`in`.web.controller

import com.cymply.user.adapter.`in`.web.dto.SignupRequest
import com.cymply.user.adapter.`in`.web.dto.SignupResponse
import com.cymply.user.adapter.`in`.web.dto.UserResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@Tag(name = "User", description = "회원 관련 API")
@RequestMapping("/api/v1/users")
interface UserApiController {
    @Operation(summary = "나의 정보 조회", description = "사용자의 정보를 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "성공"),
            ApiResponse(responseCode = "401", description = "잘못된 토큰이나 만료된 토큰인 경우"),
            ApiResponse(responseCode = "404", description = "존재하지 않거나 유효하지 않은 회원인 경우"),
        ]
    )
    @GetMapping("/me")
    fun getMyAccount(
        @AuthenticationPrincipal principal: Jwt,
    ): com.cymply.common.response.ApiResponse<UserResponse>

    @Operation(summary = "닉네임 중복 검사", description = "사용 가능한 닉네임을 검사합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "성공"),
            ApiResponse(responseCode = "401", description = "잘못된 토큰이나 만료된 토큰인 경우"),
            ApiResponse(responseCode = "404", description = "사용할 수 없는 닉네임인 경우"),
        ]
    )
    @GetMapping("/check/nickname/{nickname}")
    fun checkAvailableNickname(
        @AuthenticationPrincipal principal: Jwt,
        @PathVariable nickname: String
    ): com.cymply.common.response.ApiResponse<Boolean?>

    @Operation(summary = "회원가입", description = "OAuth2 회원가입 및 회원의 추가 정보 저장합니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "성공"),
            ApiResponse(responseCode = "401", description = "잘못된 토큰이나 만료된 토큰인 경우"),
        ]
    )
    @PostMapping("/signup/oauth2")
    fun signupOAuth2User(
        @AuthenticationPrincipal principal: Jwt,
        @RequestBody request: SignupRequest
    ): com.cymply.common.response.ApiResponse<SignupResponse?>
}
