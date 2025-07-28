package com.cymply.letter.adapter.`in`.web.controller

import com.cymply.common.response.ApiResponse
import com.cymply.letter.adapter.`in`.web.dto.*
import com.cymply.letter.application.dto.GetLetterQuery
import com.cymply.letter.application.dto.SendLetterCommand
import com.cymply.letter.application.dto.SetLetterNicknameCommand
import com.cymply.letter.application.port.`in`.*
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/letters")
class LetterController(
    private val setLetterNicknameUseCase: SetLetterNicknameUseCase,
    private val getLetterNicknameUseCase: GetLetterNicknameUseCase,
    private val sendLetterUseCase: SendLetterUseCase,
    private val getLetterUseCase: GetLetterUseCase,
) : LetterApiController {

    @GetMapping("/{id}")
    override fun getLetter(
        @AuthenticationPrincipal principal: Jwt,
        @PathVariable id: Long
    ): ApiResponse<LetterResponse> {
        val userId = principal.getClaimAsString("id").toLong()
        val query = GetLetterQuery.of(userId, id)
        val result = getLetterUseCase.getLetter(query)
        return ApiResponse.success(LetterResponse.from(result))
    }

    @GetMapping("/received/grouped")
    override fun getGroupedReceivedLetters(
        @AuthenticationPrincipal principal: Jwt,
    ): ApiResponse<List<GetReceivedLetterGroupResponse>> {
        val userId = principal.getClaimAsString("id").toLong()
        val result = getLetterUseCase.getLetters(userId)
        return ApiResponse.success(GetReceivedLetterGroupResponse.from(result))
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

    override fun getLettersCount(
        @AuthenticationPrincipal principal: Jwt,
    ): ApiResponse<LetterCountResponse> {
        val id = principal.getClaimAsString("id").toLong()
        val result = getLetterUseCase.getCounts(id)
        val response = LetterCountResponse(receivedCount = result.receivedCount, sentCount = result.sentCount)
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