package com.cymply.auth.adapter.`in`.security.dto

import com.cymply.common.model.Gender
import org.springframework.security.oauth2.core.user.OAuth2User
import java.time.LocalDate
import java.time.format.DateTimeParseException

class KakaoPrincipalDetail(
    id: Long,
    email: String,
    role: String,
    sub: String,
    provider: String,
    @get:JvmName("getName_") val name: String? = null,
    val gender: Gender? = null,
    val birth: LocalDate? = null,
) : PrincipalDetail(id, email, role, sub, provider) {
    companion object {
        fun from(id: Long, role: String, user: OAuth2User): KakaoPrincipalDetail {
            val attributes = user.attributes
            val sub = attributes["id"]?.toString() ?: throw IllegalArgumentException("sub is null")
            val account = attributes["kakao_account"] as Map<*, *>
            val email = account["email"] as String
            // optional
            val name = account["name"] as? String
            val gender = account["gender"] as? String
            val gender2 = gender?.let { Gender.from(it) }
            val birthyear = attributes["birthyear"] as? String
            val birthday = attributes["birthday"] as? String
            val birth = convertLocalDate(birthyear, birthday)
            return KakaoPrincipalDetail(id, email, role, sub, "KAKAO", name, gender2, birth)
        }

        private fun convertLocalDate(year: String?, monthday: String?): LocalDate? {
            try {
                if (year != null && monthday != null) {
                    val month = monthday.substring(0, 2).toInt()
                    val day = monthday.substring(2).toInt()
                    return LocalDate.of(year.toInt(), month, day)
                } else {
                    return null
                }
            } catch (ex: DateTimeParseException) {
                return null
            }
        }
    }

    override fun getAttributes(): Map<String, Any?> = mapOf(
        "id" to id,
        "email" to email,
        "role" to role,
        "sub" to sub,
        "gender" to gender,
        "name" to name,
        "birth" to birth,
        "provider" to provider
    )
}