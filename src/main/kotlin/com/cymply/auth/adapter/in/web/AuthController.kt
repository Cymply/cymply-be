package com.cymply.auth.adapter.`in`.web

import com.cymply.auth.adapter.`in`.AuthApiController
import org.springframework.web.bind.annotation.RestController
import java.util.IllformedLocaleException

@RestController
class AuthController : AuthApiController {
    override fun oAuth2Login(provider: String) {
        throw IllformedLocaleException("spring oauth2 client")
    }

    override fun issueAccessToken(refreshToken: String) {
        TODO("Not yet implemented")
    }
}