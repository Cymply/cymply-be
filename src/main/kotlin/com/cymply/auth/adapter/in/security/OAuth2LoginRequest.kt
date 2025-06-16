package com.cymply.auth.adapter.`in`.security

import com.cymply.auth.application.port.`in`.GoogleOAuth2LoginCommand
import com.cymply.auth.application.port.`in`.KakaoOAuth2LoginCommand
import com.cymply.auth.application.port.`in`.OAuth2LoginCommand
import com.cymply.common.model.Gender
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

data class OAuth2LoginRequest(
    val attributes: Map<String, Any?>,
    val registration: String
) {
    fun toOAuth2LoginCommand(): OAuth2LoginCommand {
        return when (registration) {
            "google" -> toGoogleCommand()
            "kakao" -> toKakaoCommand()
            else -> throw IllegalArgumentException("Unrecognized registration: $registration")
        }
    }

    private fun toGoogleCommand(): OAuth2LoginCommand {
        return GoogleOAuth2LoginCommand(
            sub = attributes["sub"] as String,
            email = attributes["email"] as String,
            name = attributes["name"] as String,
            provider = registration.uppercase()
        )
    }

    private fun toKakaoCommand(): OAuth2LoginCommand {
        val sub = (attributes["id"] as Number).toString()
        val account = attributes["kakao_account"] as Map<*, *>
        return KakaoOAuth2LoginCommand(
            sub = sub,
            email = account["email"] as String,
            name = account["name"] as? String,
            birth = formatLocalDate(
                account["birthyear"] as? String,
                account["birthday"] as? String
            ),
            gender = (account["gender"] as? String)?.let { Gender.from(it) },
            provider = registration.uppercase()
        )
    }

    private fun formatLocalDate(year: String?, monthday: String?): LocalDate? {
        return try {
            if (year != null && monthday != null) {
                LocalDate.parse(
                    year + monthday,
                    DateTimeFormatter.ofPattern("yyyyMMdd")
                )
            } else null
        } catch (ex: DateTimeParseException) {
            null
        }
    }
}