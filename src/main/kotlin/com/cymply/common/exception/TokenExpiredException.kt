package com.cymply.common.exception

class TokenExpiredException(message: String = "Token is expired")
    : RuntimeException(message)
