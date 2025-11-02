package com.songify.domain.crud;

import org.springframework.data.domain.Pageable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class InMemoryArtistRepository implements ArtistRepository {

    Map<Long, Artist> artists = new HashMap<>();
    AtomicInteger atomicInteger = new AtomicInteger(0);


    @Override
    public Artist save(Artist artist) {
        long index = atomicInteger.getAndIncrement();
        artists.put(index, artist);
        artist.setId(index);
        return artist;
    }

    @Override
    public Set<Artist> findAll(Pageable pageable) {
        return artists.values().stream().collect(Collectors.toSet());
    }

    @Override
    public Optional<Artist> findById(Long artistId) {
        return Optional.ofNullable(artists.get(artistId));
    }

    @Override
    public int deleteById(Long id) {
        return artists.remove(id) != null ? 1 : 0;
    }

    @Override
    public void removeAlbumFromArtist(Long artistId, Long albumId) {

    }
}
