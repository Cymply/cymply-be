package com.cymply.letter.application.port.out

import com.cymply.letter.application.dto.LetterSummary
import com.cymply.letter.domain.Letter

interface LoadLetterPort {

    fun loadById(id: Long): Letter
    fun loadSentCountByUserId(userId: Long): Long
    fun loadReceivedCountByUserId(userId: Long): Long
    fun loadAllByUserId(userId: Long): List<LetterSummary>
}