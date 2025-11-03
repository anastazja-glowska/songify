package com.songify.domain.crud;

public class ArtistNotFoundException extends RuntimeException {
    ArtistNotFoundException(String message) {
        super(message);
    }
}
