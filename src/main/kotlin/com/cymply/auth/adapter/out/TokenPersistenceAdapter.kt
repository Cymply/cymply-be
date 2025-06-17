package com.cymply.auth.adapter.out

import com.cymply.auth.application.port.out.LoadTokenPort
import com.cymply.auth.application.port.out.SaveTokenPort
import org.springframework.stereotype.Component

@Component
class TokenPersistenceAdapter() : LoadTokenPort, SaveTokenPort {

    override fun loadToken(token: String): String? {
        TODO("not implemented")
    }
    override fun saveRefreshToken(token: String) {
    }
}