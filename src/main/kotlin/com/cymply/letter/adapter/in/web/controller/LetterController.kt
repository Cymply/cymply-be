package com.cymply.letter.adapter.`in`.web.controller

import com.cymply.common.response.ApiResponse
import com.cymply.letter.adapter.`in`.web.dto.*
import com.cymply.letter.application.dto.SendLetterCommand
import com.cymply.letter.application.dto.SetLetterNicknameCommand
import com.cymply.letter.application.port.`in`.*
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/letters")
class LetterController(
    private val setLetterNicknameUseCase: SetLetterNicknameUseCase,
    private val getLetterNicknameUseCase: GetLetterNicknameUseCase,
    private val sendLetterUseCase: SendLetterUseCase,
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

    override fun sendLetter(
        @AuthenticationPrincipal principal: Jwt,
        request: SendLetterRequest
    ): ApiResponse<Unit> {
        val id = principal.getClaimAsString("id").toLong()
        val command = SendLetterCommand.of(id, request.recipientCode, request.content, request.title, request.artist)
        sendLetterUseCase.sendLetter(command)
        return ApiResponse.success(Unit)
    }


    override fun setLetterNickname(
        @AuthenticationPrincipal principal: Jwt,
        @RequestBody request: SetLetterNicknameRequest
    ): ApiResponse<Unit> {
        val id = principal.getClaimAsString("id").toLong()
        val command = SetLetterNicknameCommand(id, request.recipientCode, request.nickname)
        setLetterNicknameUseCase.setLetterNickname(command)
        return ApiResponse.success(Unit)
    }

    override fun getLetterNickname(
        @AuthenticationPrincipal principal: Jwt,
        @RequestParam recipientCode: String,
    ): ApiResponse<LetterNicknameResponse> {
        val id = principal.getClaimAsString("id").toLong()
        val info = getLetterNicknameUseCase.getLetterNickname(id, recipientCode)
        val response = LetterNicknameResponse.from(info)
        return ApiResponse.success(response)
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