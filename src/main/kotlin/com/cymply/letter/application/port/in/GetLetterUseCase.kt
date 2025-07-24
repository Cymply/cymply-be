package com.cymply.letter.application.port.`in`

import com.cymply.letter.application.dto.GetLetterCountResult
import com.cymply.letter.application.dto.GetLetterQuery
import com.cymply.letter.application.dto.GetLetterResult
import com.cymply.letter.application.dto.LetterSummary

interface GetLetterUseCase {

    fun getLetter(query: GetLetterQuery): GetLetterResult
    fun getLetters(userId: Long): List<LetterSummary>
    fun getCounts(userId: Long): GetLetterCountResult
}