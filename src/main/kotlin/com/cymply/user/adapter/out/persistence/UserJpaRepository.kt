package com.cymply.user.adapter.out.persistence

import com.cymply.user.adapter.out.persistence.entity.OAuth2UserEntity
import com.cymply.user.adapter.out.persistence.entity.UserEntity
import com.cymply.user.domain.UserProvider
import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository : JpaRepository<UserEntity, Long> {
    fun findByNickname(nickname: String): UserEntity?
}

interface OAuth2UserJpaRepository : JpaRepository<OAuth2UserEntity, Long> {
    fun findBySubAndProvider(sub: String, provider: UserProvider): OAuth2UserEntity?
}