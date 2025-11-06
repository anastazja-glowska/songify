package com.songify.infrastructure.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(value = "auth.jwt")
public record JwtConfiguationProperties(String secret, Long expirationMinutes, String issuer) {
}
