package com.cymply.playlist.adapter.`in`.web.controller

import com.cymply.playlist.adapter.`in`.web.dto.CreatePlaylistRequest
import com.cymply.playlist.adapter.`in`.web.dto.CreatePlaylistResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springdoc.core.annotations.ParameterObject
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

@Tag(name = "Playlist", description = "플레이리스트 관련 API")
@RequestMapping("/api/v1/playlists")
interface PlaylistApiController {

    @Operation(summary = "플레이리스트 생성", description = "플레이리스트를 생성합니다.")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "성공"
            ),
            ApiResponse(responseCode = "400", description = "잘못된 요청")
        ]
    )
    @PostMapping
    fun createPlaylist(
        @ParameterObject @RequestBody request: CreatePlaylistRequest
    ) : com.cymply.common.response.ApiResponse<CreatePlaylistResponse>
}