package com.songify.infrastructure.crud.artist;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ArtistUpdateRequestDto(
        @NotNull(message = "Artist name must not be null")
        @NotEmpty(message = "Artist name must not be empty")
        String newArtistName) {
}
