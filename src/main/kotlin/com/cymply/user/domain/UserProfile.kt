package com.cymply.user.domain

import com.cymply.common.model.Gender

data class UserProfile(
    val gender: Gender?,
    val ageRange: AgeRange?
) {
    enum class AgeRange(
        val start: Int,
        val end: Int,
        val description: String
    ) {
        AGE_UNDER_10(0, 9, "9세 이하"),
        AGE_10_19(10, 19, "10~19세"),
        AGE_20_24(20, 24, "20~24세"),
        AGE_25_30(25, 30, "25~30세"),
        AGE_OVER_30(31, Int.MAX_VALUE, "31세 이상");

        fun contains(age: Int): Boolean = age in start..end

        companion object {
            fun from(age: Int): AgeRange {
                return entries.firstOrNull { it.contains(age) }
                    ?: throw IllegalArgumentException("$age is not a valid age")
            }

            fun from(ageRange: String): AgeRange {
                return entries.firstOrNull { it.name == ageRange }
                    ?: throw IllegalArgumentException("$ageRange is not a valid age")
            }
        }
    }

}