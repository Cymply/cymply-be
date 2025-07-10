package com.cymply.letter.adapter.`in`.web.controller

import com.cymply.common.response.ApiResponse
import com.cymply.letter.adapter.`in`.web.dto.*
import com.cymply.letter.application.port.`in`.SetNicknameCommand
import com.cymply.letter.application.port.`in`.SetNicknameUseCase
import com.cymply.music.adapter.`in`.web.dto.SearchMusicResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/letters")
class LetterController(
    private val setNicknameUseCase: SetNicknameUseCase
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

    @PostMapping
    override fun sendLetter(
        @AuthenticationPrincipal principal: Jwt,
        request: SendLetterRequest
    ): ApiResponse<Unit> {
        return ApiResponse.success(Unit)
    }


    @PostMapping("/code/{code}/nickname")
    override fun setNickname(
        @AuthenticationPrincipal principal: Jwt,
        @PathVariable code: String,
        @RequestBody request: SetNicknameRequest
    ): ApiResponse<Unit> {
        val id = principal.getClaimAsString("id").toLong()
        val command = SetNicknameCommand(senderId = id, code = code, request.nickname)
        setNicknameUseCase.setNickname(command)
        return ApiResponse.success(Unit)
    }

    @GetMapping("/code/{code}/nickname")
    override fun getNickname(
        @AuthenticationPrincipal principal: Jwt,
        @PathVariable code: String,
    ): ApiResponse<Unit> {
        TODO("Not yet implemented")
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