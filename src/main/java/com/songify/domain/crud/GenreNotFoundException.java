package com.songify.domain.crud;

public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(String s) {
        super(s);
    }
}
