package com.cymply.letter.adapter.out.persistence.mapper

import com.cymply.letter.adapter.out.persistence.entity.LetterEntity
import com.cymply.letter.domain.Letter
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface LetterEntityMapper {
    fun to(domain: Letter): LetterEntity

    fun to(entity: LetterEntity): Letter
}