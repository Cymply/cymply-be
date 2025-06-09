package com.cymply.user.adapter.out.persistence

import com.cymply.user.adapter.out.persistence.entity.OAuth2UserEntity
import com.cymply.user.adapter.out.persistence.entity.UserEntity
import com.cymply.user.domain.OAuth2User
import com.cymply.user.domain.User
import org.mapstruct.*

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION
)
interface UserEntityMapper {
    @SubclassMapping(source = OAuth2User::class, target = OAuth2UserEntity::class)
    fun from(user: User): UserEntity

    @SubclassMapping(source = OAuth2UserEntity::class, target = OAuth2User::class)
    fun from(userEntity: UserEntity): User
}
