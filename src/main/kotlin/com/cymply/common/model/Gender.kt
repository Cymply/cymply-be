package com.cymply.common.model

enum class Gender(
    val value1: String,
    val description: String,
) {
    F("female", "여성"),
    M("male", "남성");

    companion object {
        fun from(value1: String): Gender? {
            return when (value1) {
                "female" -> F
                "male" -> M
                else -> null
            }
        }
    }
}