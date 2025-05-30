package com.cymply.user.application.service

import com.cymply.user.application.port.`in`.GetUserUseCase
import com.cymply.user.application.port.`in`.UserInfo
import com.cymply.user.application.port.out.LoadUserPort
import com.cymply.user.domain.UserProvider
import org.springframework.stereotype.Service

@Service
class GetUserService(
    private val loadUserPort: LoadUserPort
) : GetUserUseCase {
    override fun getUserByEmail(email: String): UserInfo? {
        TODO("Not yet implemented")
    }

    override fun getActiveUser(provider: String, sub: String): UserInfo? {
        val user = loadUserPort.loadUserBySub(sub)
        if (user == null || !user.isActiveUser()) {
            return null
        }
        user.verifyOAuth2Method(UserProvider.valueOf(provider.uppercase()))
        return UserInfo.from(user)
    }
}