package com.cymply.auth.adapter.out

import com.cymply.auth.application.port.out.SaveRefreshTokenPort
import org.springframework.stereotype.Component

@Component
class RefreshTokenPersistenceAdapter: SaveRefreshTokenPort {
    override fun saveRefreshToken(token: String) {
        TODO("Not yet implemented")
    }
}