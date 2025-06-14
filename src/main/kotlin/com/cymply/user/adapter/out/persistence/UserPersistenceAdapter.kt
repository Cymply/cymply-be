package com.cymply.user.adapter.out.persistence

import com.cymply.user.application.port.out.LoadUserPort
import com.cymply.user.application.port.out.SaveUserPort
import com.cymply.user.domain.User
import com.cymply.user.domain.UserProvider
import org.springframework.stereotype.Repository

@Repository
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository,
    private val oAuth2UserJpaRepository: OAuth2UserJpaRepository,
    private val userEntityMapper: UserEntityMapper
) : LoadUserPort, SaveUserPort {
    override fun loadUserById(id: Long): User {
        val userEntity = userJpaRepository.findById(id)
            .orElseThrow { IllegalArgumentException("User not found") }
        return userEntityMapper.from(userEntity)
    }

    override fun loadUserBySubAndProvider(sub: String, provider: UserProvider): User? {
        val userEntity = oAuth2UserJpaRepository.findBySubAndProvider(sub, provider)
            ?: return null
        return userEntityMapper.from(userEntity)
    }

    override fun loadUserByNickname(nickname: String): User? {
        val userEntity = userJpaRepository.findByNickname(nickname) ?: return null
        return userEntityMapper.from(userEntity)
    }

    override fun saveUser(user: User): Long {
        val userEntity = userEntityMapper.from(user)
        userJpaRepository.save(userEntity)
        return userEntity.id ?: 0L
    }
}