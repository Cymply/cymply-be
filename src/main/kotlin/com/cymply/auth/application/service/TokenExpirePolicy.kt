package com.cymply.auth.application.service

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.security.jwt.token-expire-policy")
data class TokenExpirePolicy(
    var access: Long,
    var refresh: Long,
    var temporal: Long
)