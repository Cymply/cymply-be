package com.cymply.user.application.service

import com.cymply.user.application.port.`in`.WithdrawUserUserCase
import com.cymply.user.application.port.out.LoadUserPort
import com.cymply.user.application.port.out.SaveUserPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WithdrawUserService(
    private val loadUserPort: LoadUserPort,
    private val saveUserPort: SaveUserPort
) : WithdrawUserUserCase {

    @Transactional
    override fun withdrawActiveUser(id: Long) {
        val user = loadUserPort.loadUserById(id)
        user.withdraw()

        saveUserPort.saveUser(user)
    }
}