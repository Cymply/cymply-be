package com.cymply.letter.application.service

import com.cymply.letter.application.port.`in`.SendLetterCommand
import com.cymply.letter.application.port.`in`.SendLetterUseCase
import com.cymply.letter.application.port.out.SaveLetterPort
import com.cymply.letter.domain.Letter
import com.cymply.music.application.port.dto.PlayMusicQuery
import com.cymply.music.application.port.`in`.FindMusicOrCreateUseCase
import com.cymply.user.application.port.`in`.GetRecipientUseCase
import org.springframework.stereotype.Service

@Service
class SendLetterService(
    val getMusicOrCreateUseCase: FindMusicOrCreateUseCase,
    val getRecipientUseCase: GetRecipientUseCase,
    val saveLetterPort: SaveLetterPort
) : SendLetterUseCase {
    override fun sendLetter(command: SendLetterCommand): Long {
        val recipient = getRecipientUseCase.getRecipient(command.recipientCode)

        val query = PlayMusicQuery(command.musicTitle, command.musicArtist)
        val music = getMusicOrCreateUseCase.findMusicOrCreate(query)

        val letter = Letter.of(command.title, command.content, music.id, recipient.id, command.senderId)
        return saveLetterPort.save(letter)
    }
}