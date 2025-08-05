package com.cymply.auth.application.port.`in`

import com.cymply.common.model.Gender
import java.time.LocalDate


sealed interface OAuth2LoginCommand {
    val sub: String
    val email: String
    val name: String?
    val provider: String
    fun getAttributes(): Map<String, Any?>
}

data class GoogleOAuth2LoginCommand(
    override val sub: String,
    override val email: String,
    override val name: String,
    override val provider: String = "GOOGLE"
) : OAuth2LoginCommand {
    override fun getAttributes(): Map<String, Any?> {
        return mapOf(
            "sub" to sub,
            "email" to email,
            "name" to name,
            "provider" to provider
        )
    }
}

data class KakaoOAuth2LoginCommand(
    override val sub: String,
    override val email: String,
    override val name: String?,
    override val provider: String = "KAKAO",
    val birth: LocalDate?,
    val gender: Gender?
) : OAuth2LoginCommand {
    override fun getAttributes(): Map<String, Any?> {
        return mapOf(
            "sub" to sub,
            "email" to email,
            "name" to name,
            "provider" to provider,
            "birth" to birth,
            "gender" to gender
        )
    }
}
