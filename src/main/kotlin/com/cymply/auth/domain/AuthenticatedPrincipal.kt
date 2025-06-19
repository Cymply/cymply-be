package com.cymply.auth.domain

class AuthenticatedPrincipal(
    val id: Long,
    val email: String,
    val nickname: String,
    val scopes: List<String>
) {
    companion object {
        fun of(id: Long, email: String, nickname: String, role: String): AuthenticatedPrincipal {
            val authorities = when (role) {
                "USER" -> listOf("user", "letter", "music", "playlist")
                "ADMIN" -> listOf("admin")
                else -> listOf()
            }
            return AuthenticatedPrincipal(id, email, nickname, authorities)
        }
    }

    fun getAttributes() = mapOf(
        "id" to id,
        "email" to email,
        "nickname" to nickname,
        "scope" to scopes.joinToString(" ")
    )
}