package com.songify.domain.crud;

import com.songify.domain.crud.dto.AlbumDto;


import com.songify.domain.crud.dto.AlbumWithArtistsAndSongsDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.SongDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class AlbumRetriever {

    private final AlbumRepository albumRepository;


    AlbumWithArtistsAndSongsDto findAlbumByIdWithArtistsAndSongs(Long id) {
        Album album = albumRepository.findAlbumByIdWithArtistsAndSongs(id).orElseThrow(() ->
                new AlbumNotFoundEcxeption("Album with id " + id + " not found"));

        Set<Artist> artists = album.getArtists();
        Set<Song> songs = album.getSongs();


        AlbumDto albumDto = new AlbumDto(album.getId(), album.getTitle(), album.getSongIds());

        Set<ArtistDto> artistDtoSet = artists.stream()
                .map(artist -> new ArtistDto( artist.getId(), artist.getName()))
                .collect(Collectors.toSet());

        Set<SongDto> songDtos = songs.stream()
                .map(song -> new SongDto(song.getId(), song.getName(),
                        new GenreDto(song.getGenre().getId(), song.getGenre().getName())))
                .collect(Collectors.toSet());

        return  new AlbumWithArtistsAndSongsDto(albumDto, artistDtoSet, songDtos);


    }

    Set<Album> findAlbumsByArtistId(final Long artistId) {
        return albumRepository.findAlbumsByArtistId(artistId);
    }

    Album findById(Long albumId) {
         return albumRepository.findById(albumId).orElseThrow(() ->
                 new AlbumNotFoundEcxeption("Album with id " + albumId + " not found"));
    }

    AlbumDto findAlbumDtoById(Long albumId) {
        Album album = albumRepository.findById(albumId).orElseThrow(() ->
                new AlbumNotFoundEcxeption("Album with id " + albumId + " not found"));

        return new AlbumDto(album.getId(), album.getTitle(), album.getSongIds());
    }

    Set<AlbumDto> findAlbumsDtoByArtistId(final Long artistId) {


        return findAlbumsByArtistId(artistId).stream()
                .map(album -> new AlbumDto(album.getId(), album.getTitle(), album.getSongIds()))
                .collect(Collectors.toSet());

    }


    Long countArtistsByAlbumId(Long albumId) {
        return findAlbumByIdWithArtistsAndSongs(albumId).artists().stream().count();
    }

    Set<AlbumDto> findAll() {
        return albumRepository.findAll().stream()
                .map(album -> new AlbumDto(album.getId(), album.getTitle(), album.getSongIds()))
                .collect(Collectors.toSet());
    }
}
