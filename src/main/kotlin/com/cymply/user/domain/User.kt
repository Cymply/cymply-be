package com.cymply.user.domain

import java.time.LocalDateTime

class User(
    val id: Long? = null,
    val email: String,
    val password: String? = null,
    val name: String? = null,
    val nickname: String? = null,
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
        fun join(
            email: String,
            provider: String?,
            sub: String?,
            name: String?,
            nickname: String?,
        ) = User(
            email = email,
            provider = provider?.let { UserProvider.valueOf(it.uppercase()) },
            name = name,
            nickname = nickname,
            sub = sub
        )
    }

    /***
     * 자체(이메일) 회원가입
     */
    fun join(
        email: String,
        password: String,
        nickname: String?,
    ): User {
        TODO("Not yet implemented")
    }

    fun verifyValidUser(provider: String?) {
        provider?.let { verifyMethod(UserProvider.valueOf(provider.uppercase())) }
        verifyActiveUser()
    }

    private fun verifyMethod(provider: UserProvider) {
        if (this.provider != provider) {
            throw IllegalArgumentException("already join to${this.provider?.name}")
        }
    }

    private fun verifyActiveUser() {
        if (deletedAt != null) {
            throw IllegalArgumentException("already quit user $email")
        }
    }
}
