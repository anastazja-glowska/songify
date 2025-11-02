package com.songify.domain.crud;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class InMemoryAlbumRepository implements AlbumRepository {


    Map<Long, Album> db = new HashMap<>();
    AtomicInteger count = new AtomicInteger(0);

    @Override
    public Album save(Album album) {
        long id = count.getAndIncrement();
        db.put(id, album);
        album.setId(id);
        return album;
    }

    @Override
    public Optional<Album> findAlbumByIdWithArtistsAndSongs(Long id) {
        Album album = db.get(id);
        return Optional.ofNullable(album);
    }

    @Override
    public Set<Album> findAlbumsByArtistId(Long id) {
        return db.values().stream()
                .filter(album -> album.getArtists().stream()
                        .anyMatch(artist -> artist.getId().equals(id))).collect(Collectors.toSet());
    }

    @Override
    public int deleteByIdIn(Collection<Long> id) {
        id.forEach(i -> db.remove(i));
        return 0;
    }

    @Override
    public void deleteById(Long id) {
        Album album = db.get(id);
        if(album.getArtists().size() == 1){
            db.remove(id);
        }
    }

//    @Override
//    public void deleteAlbumsByIds(Set<Long> ids) {
//
//    }

    @Override
    public Optional<Album> findById(Long id) {
        return Optional.ofNullable(db.get(id));
    }

    @Override
    public Set<Album> findAll() {
        return  new HashSet<>(db.values());
    }
}
