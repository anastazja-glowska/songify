package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;
import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.album.AlbumWithSongsResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
class AlbumAdder {

    private final   SongRetrierver songRetrierver;
    private final AlbumRepository albumRepository;

    AlbumDto addAlbum(Set<Long> songIds, String title, Instant instant) {

        Set<Song> songs = songIds.stream().map(id -> songRetrierver.findSongById(id)).collect(Collectors.toSet());

        Album album = new Album();
        album.setTitle(title);
        album.addSongsToAlbum(songs);

        album.setReleaseDate(instant);
        Album savedAlbum = albumRepository.save(album);
        Set<Long> songsIds = songs.stream().map(song -> song.getId()).collect(Collectors.toSet());
        return new AlbumDto(savedAlbum.getId(), savedAlbum.getTitle(), songsIds);
    }


    Album addAlbum(final String title, final Instant instant) {
        Album album = new Album();
        album.setTitle(title);
        album.setReleaseDate(instant);
        return albumRepository.save(album);

    }

    AlbumWithSongsResponseDto addSongToAlbum(Long songId, Long albumId) {
        Song song = songRetrierver.findSongById(songId);
        Album album = albumRepository.findById(albumId).orElseThrow(() ->
                new AlbumNotFoundEcxeption("Album with id " + albumId + " not found"));
        album.addSongToAlbum(song);

        Set<Long> songIds = album.getSongIds();
        return new AlbumWithSongsResponseDto(album.getId(), album.getTitle(), songIds );

    }

}
