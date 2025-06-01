package com.cymply.user.domain

import java.time.LocalDateTime

class User(
    val id: Long? = null,
    val email: String,
    val password: String? = null,
    val name: String? = null,
    val birth: String? = null,
    /**
     * 소셜 플랫폼 종류
     */
    val provider: UserProvider? = null,
    /**
     * 소셜 플랫폼 식별자
     */
    val sub: String? = null,
    val deletedAt: LocalDateTime? = null
) {
    companion object {
        /***
         * 소셜 회원가입
         */
        fun of(
            sub: String,
            provider: String,
            email: String,
            name: String?,
            birth: String?
        ) = User(
            email = email,
            provider = provider.let { UserProvider.valueOf(it.uppercase()) },
            sub = sub,
            name = name,
            birth = birth
        )

        /***
         * 자체(이메일) 회원가입
         */
        fun of(
            email: String,
            password: String,
            name: String?,
        ) = User(
            email = email,
            password = password,
            name = name
        )
    }

    fun getIdOrThrow() = id ?: throw IllegalStateException("User id is null.")

    fun verifyValidUser() {
        if (!isActiveUser()) {
            throw IllegalArgumentException("Already withdraw User $id")
        }
    }

    fun verifyOAuth2Method(provider: UserProvider) {
        if (this.provider != provider) {
            throw IllegalArgumentException("Invalid OAuth2 provider. Expected: $provider, but was: ${this.provider}")
        }
    }

    fun isActiveUser() = deletedAt == null
}
