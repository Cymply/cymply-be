package com.cymply.auth.adapter.`in`.security

import com.cymply.auth.application.service.OAuth2LoginCommand
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User

class OAuth2LoginCommandConverter {
    companion object {
        fun convert(request: OAuth2UserRequest, oAuth2User: OAuth2User): OAuth2LoginCommand {
            return when (request.clientRegistration.registrationId) {
                "google" -> convertToGoogleUserInfo(oAuth2User.attributes)
                "kakao" -> convertToKakaoUserInfo(oAuth2User.attributes)
                else -> throw IllegalArgumentException("invalid login method")
            }
        }

        private fun convertToGoogleUserInfo(attributes: Map<String?, Any?>): OAuth2LoginCommand {
            val id = attributes.getOrDefault("sub", null) as String
            val email = attributes.getOrDefault("email", null) as String
            val name = attributes.getOrDefault("name", null) as String
            return OAuth2LoginCommand(
                id = id,
                provider = "GOOGLE",
                email = email,
                name = name,
                birth = null
            )
        }

        private fun convertToKakaoUserInfo(attributes: Map<String?, Any>): OAuth2LoginCommand {
            val id = attributes["id"] as? String ?: throw IllegalArgumentException("id is null")
            val account = attributes["kakao_account"] as Map<*, *>
            val email = account["email"] as? String ?: throw IllegalArgumentException("email is null")
            val profile = account["profile"] as Map<*, *>
            val nickname = profile["nickname"] as? String
            return OAuth2LoginCommand(
                id = id,
                provider = "KAKAO",
                email = email,
                name = nickname,
                birth = null
            )
        }
    }
}