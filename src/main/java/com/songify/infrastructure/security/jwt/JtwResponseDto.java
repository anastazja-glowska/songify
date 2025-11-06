package com.songify.infrastructure.security.jwt;

import lombok.Builder;

@Builder
public record JtwResponseDto(String token) {
}
