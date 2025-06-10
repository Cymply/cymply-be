package com.cymply.auth.adapter.`in`.security

import org.springframework.security.oauth2.core.user.OAuth2User

data class OAuth2UserAccount(
    val provider: String,
    val sub: String,
    val email: String,
    val name: String?
) {
    companion object {
        fun from(provider: String, oAuth2User: OAuth2User): OAuth2UserAccount {
            return when (provider) {
                "GOOGLE" -> fromGoogleAttribute(oAuth2User.attributes)
                "KAKAO" -> fromKakaoAttribute(oAuth2User.attributes)
                else -> throw IllegalArgumentException("Invalid provider")
            }
        }

        private fun fromGoogleAttribute(attributes: Map<String?, Any>): OAuth2UserAccount {
            val sub = attributes["sub"] as String
            val email = attributes["email"] as String
            val name = attributes["name"] as String
            return OAuth2UserAccount("GOOGLE", sub, email, name)
        }

        private fun fromKakaoAttribute(attributes: Map<String?, Any>): OAuth2UserAccount {
            val sub = attributes["id"] as String
            val account = attributes["kakao_account"] as Map<*, *>
            val email = account["email"] as String
            val name = account["name"] as? String
            return OAuth2UserAccount("KAKAO", sub, email, name)
        }
    }

    fun getAttributes(): Map<String, Any?> {
        return mutableMapOf(
            "provider" to provider,
            "sub" to sub,
            "email" to email,
            "name" to name
        )
    }
}