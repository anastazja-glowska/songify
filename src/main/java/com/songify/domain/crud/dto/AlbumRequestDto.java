package com.songify.domain.crud.dto;

import lombok.Builder;
import org.springframework.web.bind.annotation.BindParam;

import java.time.Instant;
import java.util.Set;

@Builder
public record AlbumRequestDto(Set<Long> songIds, String title, Instant releaseDate) {
}
