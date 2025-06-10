package com.cymply.user.application.service

import com.cymply.user.application.port.`in`.GetUserUseCase
import com.cymply.user.application.port.out.LoadUserPort
import com.cymply.user.domain.UserProvider
import org.springframework.stereotype.Service

@Service
class GetUserService(
    private val loadUserPort: LoadUserPort
) : GetUserUseCase {
    override fun getActiveUserOrElseThrow(id: Long): UserSimpleInfo {
        val user = loadUserPort.loadUserById(id)
        if (!user.isActiveUser()) {
            throw IllegalArgumentException("Expired user with id $id.")
        }
        return UserSimpleInfo.from(user)
    }

    override fun getActiveUser(provider: String, sub: String): UserSimpleInfo? {
        val user = loadUserPort.loadUserBySubAndProvider(sub, UserProvider.valueOf(provider.uppercase()))
        if (user == null || !user.isActiveUser()) {
            return null
        }
        return UserSimpleInfo.from(user)
    }
}