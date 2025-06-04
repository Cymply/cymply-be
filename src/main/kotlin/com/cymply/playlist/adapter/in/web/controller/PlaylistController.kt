package com.cymply.playlist.adapter.`in`.web.controller

import com.cymply.common.response.ApiResponse
import com.cymply.playlist.adapter.`in`.web.dto.CreatePlaylistRequest
import com.cymply.playlist.adapter.`in`.web.dto.CreatePlaylistResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/playlists")
class PlaylistController() : PlaylistApiController {

    @PostMapping
    override fun createPlaylist(request: CreatePlaylistRequest): ApiResponse<CreatePlaylistResponse> {
        return ApiResponse.success(CreatePlaylistResponse(1L, "J-POP"))
    }


}