package com.songify.domain.crud;

public class SongNotFoundException extends RuntimeException {
    public SongNotFoundException(String songNotFound) {
        super(songNotFound);
    }
}
