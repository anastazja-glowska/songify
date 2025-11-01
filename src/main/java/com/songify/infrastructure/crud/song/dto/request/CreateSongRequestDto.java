package com.songify.infrastructure.crud.song.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateSongRequestDto(
        @NotNull(message = "Song cannot be null")
        @NotEmpty(message = "Song cannot be empty")
        String songName,

        @NotNull(message = "Artist cannot be null")

        @NotEmpty(message = "Artist cannot be empty")
        String artist){
}
