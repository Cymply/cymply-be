package com.cymply.letter.adapter.`in`.web.controller

import com.cymply.letter.adapter.`in`.web.dto.LetterCodeResponse
import com.cymply.letter.adapter.`in`.web.dto.LetterResponse
import com.cymply.letter.adapter.`in`.web.dto.SendLetterRequest
import com.cymply.letter.adapter.`in`.web.dto.SenderGroupedLettersResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*


@Tag(name = "Letter", description = "편지 관련 API")
@RequestMapping("/api/v1/letters")
interface LetterApiController {
    // Query
    @Operation(summary = "편지 단건 조회", description = "편지 ID로 편지를 상세 조회합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
            ),
            ApiResponse(responseCode = "404", description = "유효하지 않은 편지 ID")
        ]
    )
    @GetMapping("/{id}")
    fun getLetter(
        @Parameter(
            description = "조회하려는 편지의 ID",
            example = "1000",
            `in` = ParameterIn.PATH,
        )
        @PathVariable id: Long
    ): com.cymply.common.response.ApiResponse<LetterResponse>


    @Operation(summary = "받은 편지 목록", description = "받은 편지 목록을 확인합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "성공",
            ),
            ApiResponse(responseCode = "404", description = "편지 목록 예외")
        ]
    )
    @GetMapping("/received/grouped")
    fun getGroupedReceivedLetters(
    ): com.cymply.common.response.ApiResponse<List<SenderGroupedLettersResponse>>


    // Command
    @Operation(summary = "편지 작성 링크 생성", description = "링크를 통해 특정 사용자에게 편지를 보낼 수 있습니다.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "성공"),
            ApiResponse(responseCode = "404", description = "편지 전송 중 오류가 발생했습니다.")
        ]
    )
    @PostMapping("/code")
    fun createLetterCode(
        @AuthenticationPrincipal principal: Jwt
    ): com.cymply.common.response.ApiResponse<LetterCodeResponse?>


    @Operation(summary = "편지 전송", description = "특정 사용자에게 편지를 보냅니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "성공",
            ),
            ApiResponse(responseCode = "404", description = "편지 전송 중 오류가 발생했습니다.")
        ]
    )
    @PostMapping("")
    fun sendLetter(
        @AuthenticationPrincipal principal: Jwt,
        @RequestBody @Schema(implementation = SendLetterRequest::class) request: SendLetterRequest
    ): com.cymply.common.response.ApiResponse<Unit>
}