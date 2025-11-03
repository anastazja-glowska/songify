package com.songify.infrastructure.crud.album;

import java.util.Set;

public record AlbumWithSongsResponseDto(Long albumId, String name, Set<Long> songsIds) {
}
