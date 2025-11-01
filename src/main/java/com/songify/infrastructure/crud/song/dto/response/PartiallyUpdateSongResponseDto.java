package com.songify.infrastructure.crud.song.dto.response;

import com.songify.domain.crud.dto.SongDto;

public record PartiallyUpdateSongResponseDto(SongDto song) {
}
