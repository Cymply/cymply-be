package com.cymply.letter.adapter.out.persistence.mapper

import com.cymply.letter.adapter.out.persistence.entity.LetterNicknameEntity
import com.cymply.letter.domain.LetterNickname
import org.mapstruct.Mapper
import org.mapstruct.ReportingPolicy


@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
interface LetterNicknameEntityMapper {
    fun to(domain: LetterNickname): LetterNicknameEntity

    fun to(entity: LetterNicknameEntity): LetterNickname
}