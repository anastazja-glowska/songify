package com.songify.infrastructure.crud.song.dto.response;

import java.util.List;

public record GetAllSongsResponseDto(List<SongControllerResponseDto> songs) {
}
