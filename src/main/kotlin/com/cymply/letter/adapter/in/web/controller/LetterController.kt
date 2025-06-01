package com.cymply.letter.adapter.`in`.web.controller

import com.cymply.common.response.ApiResponse
import com.cymply.letter.adapter.`in`.web.dto.LetterResponse
import com.cymply.letter.adapter.`in`.web.dto.SendLetterRequest
import com.cymply.letter.adapter.`in`.web.dto.SenderGroupedLettersResponse
import com.cymply.music.adapter.`in`.web.dto.MusicResponse
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/v1/letters")
class LetterController: LetterApiController {

    @GetMapping("/{id}")
    override fun getLetter(
        @PathVariable id: Long
    ): ApiResponse<LetterResponse> {
        return ApiResponse.success(LetterResponse(1L, "", "", LocalDateTime.now(), MusicResponse(1L, "", "", "")))
    }

    @PostMapping
    override fun sendLetter(request: SendLetterRequest): ApiResponse<Unit> {
        return ApiResponse.success(Unit)
    }

    @GetMapping("/received/grouped")
    override fun getGroupedReceivedLetters(
//        @RequestParam request: GetLettersRequest
    ): ApiResponse<List<SenderGroupedLettersResponse>> {
        return ApiResponse.success(listOf(SenderGroupedLettersResponse(1L, "", emptyList())))
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