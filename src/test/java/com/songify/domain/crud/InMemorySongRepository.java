package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class InMemorySongRepository implements  SongRepository {

    Map<Long, Song> db = new HashMap<>();
    AtomicInteger atomicInteger = new AtomicInteger(0);

    @Override
    public Song save(Song song) {
        long id = atomicInteger.getAndIncrement();
        db.put(id, song);
        song.setId(id);
//        song.setGenre(new Genre(1L, "Pop"));
        return song;
    }

    @Override
    public List<Song> findAll(Pageable pageable) {
        return db.values().stream().collect(Collectors.toList());

    }

    @Override
    public Optional<Song> findById(Long id) {
        Song song = db.get(id);
        return Optional.ofNullable(song);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void updateById(Long id, Song newSong) {

    }

    @Override
    public List<Song> findAllSongsByGenreId(Long id) {
        return List.of();
    }

    @Override
    public void updateSongsGenreId(Long id) {

    }

    @Override
    public int deleteByIdIn(Collection<Long> ids) {
        ids.forEach(id -> db.remove(id));
        return 0;
    }

    @Override
    public void deleteSongsByIds(Set<Long> ids) {

    }
}
