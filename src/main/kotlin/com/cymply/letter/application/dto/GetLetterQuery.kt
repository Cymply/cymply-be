package com.cymply.letter.application.dto

data class GetLetterQuery(
    val userId: Long,
    val letterId: Long
) {
    companion object {
        fun of(userId: Long, letterId: Long) =
            GetLetterQuery(userId, letterId)
    }
}
