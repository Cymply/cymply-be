package com.cymply.letter.adapter.out.persistence

import com.cymply.letter.adapter.out.persistence.entity.LetterCodeEntity
import com.cymply.letter.domain.LetterCode
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy


@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface LetterCodeEntityMapper {
    fun to(domain: LetterCode): LetterCodeEntity

    fun to(entity: LetterCodeEntity): LetterCode
}