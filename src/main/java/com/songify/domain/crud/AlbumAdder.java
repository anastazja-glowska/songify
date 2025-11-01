package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@AllArgsConstructor
@Transactional
class AlbumAdder {

    private final   SongRetrierver songRetrierver;
    private final AlbumRepository albumRepository;

    AlbumDto addAlbum(Long aLong, String title, Instant instant) {
        Song songById = songRetrierver.findSongById(aLong);
        Album album = new Album();
        album.setTitle(title);
        album.addSongToAlbum(songById);
        album.setReleaseDate(instant);
        Album savedAlbum = albumRepository.save(album);
        return new AlbumDto(savedAlbum.getId(), savedAlbum.getTitle());
    }


    Album addAlbum(final String title, final Instant instant) {
        Album album = new Album();
        album.setTitle(title);
        album.setReleaseDate(instant);
        return albumRepository.save(album);

    }
}
