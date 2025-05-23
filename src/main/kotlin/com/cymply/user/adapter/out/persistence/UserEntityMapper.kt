package com.cymply.user.adapter.out.persistence

import com.cymply.user.domain.User
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface UserEntityMapper {
    fun toUserEntity(user: User): UserEntity

    fun toUser(userEntity: UserEntity): User
}