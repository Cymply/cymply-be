package com.cymply.user.adapter.out.persistence

import com.cymply.user.application.port.out.LoadUserPort
import com.cymply.user.application.port.out.SaveUserPort
import com.cymply.user.domain.User
import org.springframework.stereotype.Repository

@Repository
class UserPersistenceAdapter(
    private val userJpaRepository: UserJpaRepository,
    private val userEntityMapper: UserEntityMapper
) : LoadUserPort, SaveUserPort {
    override fun loadUserByEmail(email: String): User? {
        val userEntity = userJpaRepository.findByEmail(email)
        userEntity?.let { return userEntityMapper.toUser(it) }
        return null
    }

    override fun saveUser(user: User): Long {
        val userEntity = userEntityMapper.toUserEntity(user)
        userJpaRepository.save(userEntity)
        return userEntity.id ?: 0L
    }
}