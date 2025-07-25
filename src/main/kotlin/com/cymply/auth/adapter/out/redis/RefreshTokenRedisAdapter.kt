package com.cymply.auth.adapter.out.redis

import com.cymply.auth.application.port.out.DeleteRefreshTokenPort
import com.cymply.auth.application.port.out.LoadRefreshTokenPort
import com.cymply.auth.application.port.out.SaveRefreshTokenPort
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.temporal.ChronoUnit

@Component
class RefreshTokenRedisAdapter(
    private val redisTemplate: RedisTemplate<String, String>
) : LoadRefreshTokenPort, SaveRefreshTokenPort, DeleteRefreshTokenPort {
    companion object {
        const val REFRESH_TOKEN_PREFIX = "refresh_token:"
    }

    override fun loadRefreshToken(id: String): String? {
        val key = REFRESH_TOKEN_PREFIX + id
        return redisTemplate.opsForValue().get(key) as String?
    }

    override fun saveRefreshToken(id: String, refreshToken: String, ttl: Long) {
        val key = REFRESH_TOKEN_PREFIX + id
        val duration = Duration.of(ttl, ChronoUnit.MILLIS)
        return redisTemplate.opsForValue().set(key, refreshToken, duration)
    }

    override fun deleteRefreshToken(id: String): Boolean {
        val key = REFRESH_TOKEN_PREFIX + id
        return  redisTemplate.delete(key)
    }
}