package com.cymply.user.adapter.out.persistence.mapper

import com.cymply.user.adapter.out.persistence.entity.RecipientCodeEntity
import com.cymply.user.domain.RecipientCode
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy


@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface RecipientCodeEntityMapper {
    fun to(domain: RecipientCode): RecipientCodeEntity

    fun to(entity: RecipientCodeEntity): RecipientCode
}