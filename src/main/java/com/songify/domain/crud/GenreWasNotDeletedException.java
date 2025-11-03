package com.songify.domain.crud;

public class GenreWasNotDeletedException extends RuntimeException {
    public GenreWasNotDeletedException(String message) {
        super(message);
    }
}
