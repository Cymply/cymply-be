package com.cymply.letter.adapter.`in`.web.controller

import com.cymply.common.response.ApiResponse
import com.cymply.letter.adapter.`in`.web.dto.LetterCodeResponse
import com.cymply.letter.adapter.`in`.web.dto.LetterResponse
import com.cymply.letter.adapter.`in`.web.dto.SendLetterRequest
import com.cymply.letter.adapter.`in`.web.dto.SenderGroupedLettersResponse
import com.cymply.letter.application.port.`in`.CreateLetterCodeUseCase
import com.cymply.music.adapter.`in`.web.dto.SearchMusicResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/letters")
class LetterController(
    private val createLetterCodeUseCase: CreateLetterCodeUseCase
) : LetterApiController {

    @GetMapping("/{id}")
    override fun getLetter(
        @PathVariable id: Long
    ): ApiResponse<LetterResponse> {
        val content = LetterResponse(1L, "", "", LocalDateTime.now(), SearchMusicResponse("", "", ""))
        return ApiResponse.success(content = content)
    }

    @GetMapping("/received/grouped")
    override fun getGroupedReceivedLetters(
//        @RequestParam request: GetLettersRequest
    ): ApiResponse<List<SenderGroupedLettersResponse>> {
        return ApiResponse.success(listOf(SenderGroupedLettersResponse(1L, "", emptyList())))
    }

    @PostMapping("/code")
    override fun createLetterCode(
        @AuthenticationPrincipal principal: Jwt,
    ): ApiResponse<LetterCodeResponse?> {
        val id = principal.getClaimAsString("id").toLong()

        /**
         * TODO
         * 편지 작성 폼 URL 사용 시 재조정 필요
         */
        val result = createLetterCodeUseCase.createLetterCode(id)
        val response = LetterCodeResponse(result, "https://www.cymply.kr/letter?code=${result}")
        return ApiResponse.success(response)
    }


    @PostMapping
    override fun sendLetter(
        @AuthenticationPrincipal principal: Jwt,
        request: SendLetterRequest
    ): ApiResponse<Unit> {
        return ApiResponse.success(Unit)
    }


    /*

    @GetMapping("/received")
    fun getFlatReceivedLetters(
        @RequestParam request: GetLettersRequest
    ) {

    }

    @GetMapping("/sent/grouped")
    fun getGroupedSentLetters(
        @RequestParam request: GetLettersRequest
    ) {

    }

    @GetMapping("/sent")
    fun getFlatSentLetters(
        @RequestParam request: GetLettersRequest
    ) {

    }*/
}