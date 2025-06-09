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
    override fun loadUserByEmail(email: String): User? {
        userJpaRepository.findByEmail(email)?.let {
            return userEntityMapper.from(it)
        }

        return null
    }

    override fun loadUserBySubAndProvider(sub: String, provider: UserProvider): User? {
        oAuth2UserJpaRepository.findBySubAndProvider(sub, provider)?.let {
            return userEntityMapper.from(it)
        }

        return null;
    }

    override fun saveUser(user: User): Long {
        val userEntity = userEntityMapper.from(user)
        userJpaRepository.save(userEntity)
        return userEntity.id ?: 0L
    }
}