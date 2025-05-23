package com.cymply.auth.application.service

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User

data class OAuth2UserInfo(
    val id: String,
    val email: String,
    val nickname: String?,
    val provider: String?
) {
    companion object {
        fun of(request: OAuth2UserRequest, oAuth2User: OAuth2User): OAuth2UserInfo {
            return when (request.clientRegistration.registrationId) {
                "google" -> convertToGoogleUserInfo(oAuth2User.attributes)
                "kakao" -> convertToKakaoUserInfo(oAuth2User.attributes)
                else -> throw IllegalArgumentException("invalid login method")
            }
        }

        private fun convertToGoogleUserInfo(attributes: Map<String?, Any?>): OAuth2UserInfo {
            val id = attributes.getOrDefault("sub", null) as String
            val email = attributes.getOrDefault("email", null) as String
            val name = attributes.getOrDefault("name", null) as String
            return OAuth2UserInfo(id, email, name, "GOOGLE")
        }

        private fun convertToKakaoUserInfo(attributes: Map<String?, Any>): OAuth2UserInfo {
            val id = attributes["id"] as? String ?: throw IllegalArgumentException("id is null")
            val account = attributes["kakao_account"] as Map<*, *>
            val email = account["email"] as? String ?: throw IllegalArgumentException("email is null")
            val profile = account["profile"] as Map<*, *>
            val nickname = profile["nickname"] as? String
            return OAuth2UserInfo(id, email, nickname, "KAKAO")
        }
    }
}