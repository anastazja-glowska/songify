package com.songify.domain.crud.dto;

import lombok.Builder;
import org.springframework.web.bind.annotation.BindParam;

import java.time.Instant;

@Builder
public record AlbumRequestDto(Long songId, String title, Instant releaseDate) {
}
