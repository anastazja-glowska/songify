package com.songify.domain.crud;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

class InMemoryGenreRepository implements GenreRepository {

    Map<Long, Genre> genreMap = new HashMap<>();
    AtomicInteger genreId = new AtomicInteger(1);

    public InMemoryGenreRepository() {
        save(new Genre(1L, "default"));
    }

    @Override
    public Genre save(Genre genre) {
        long index = genreId.getAndIncrement();

        genreMap.put(index, genre);
        genre.setId(index);
        return genre;
    }

    @Override
    public int deleteById(Long id) {
        genreMap.remove(id);
        return 0;
    }

    @Override
    public Optional<Genre> findById(Long id) {
        Genre genre = genreMap.get(id);
        return Optional.ofNullable(genre);
    }

    @Override
    public Set<Genre> findAll() {
        return new HashSet<>(genreMap.values());
    }
}
