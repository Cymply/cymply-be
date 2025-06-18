package com.cymply.auth.adapter.out

import com.cymply.auth.application.port.out.LoadRefreshTokenPort
import com.cymply.auth.application.port.out.SaveRefreshTokenPort
import org.springframework.stereotype.Component

@Component
class RefreshRefreshTokenRedisAdapter(
) : LoadRefreshTokenPort, SaveRefreshTokenPort {
    override fun loadRefreshToken(key: String): String? {
        return null
    }

    override fun saveRefreshToken(refreshToken: String, ttl: Long) {
    }
}