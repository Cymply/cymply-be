package com.cymply.user.adapter.`in`.web

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserGetController {
    @GetMapping("/profile")
    fun getUser(@AuthenticationPrincipal principal: OAuth2User): OAuth2User {
        return principal
    }

    @PostMapping("/join/oauth2")
    fun registerUser(@AuthenticationPrincipal principal: OAuth2User): OAuth2User {
        return principal
    }
}
