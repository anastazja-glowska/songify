package com.songify.domain.crud;

import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
class ArtistAdder {

    private final ArtistRepository artistRepository;
    private final AlbumAdder albumAdder;
    private final SongAdder songAdder;

    ArtistDto addArtist(String name) {
        Artist artist = new Artist(name);
        Artist save = artistRepository.save(artist);
        return new ArtistDto(save.getId(), save.getName());
    }

    ArtistDto addArtistWithDefaultAlbumAndSong(final ArtistRequestDto dto) {
        Artist artist = saveArtistWithDefaultAlbumAndSong(dto.name());
        return new ArtistDto(artist.getId(), artist.getName());
    }

    private Artist saveArtistWithDefaultAlbumAndSong(String name) {
        Artist artist = new Artist(name);

        Album album = albumAdder.addAlbum("Default Album" + UUID.randomUUID(),
                LocalDateTime.now().toInstant(ZoneOffset.UTC));

        Song song = songAdder.addSongAndGetEntity(new SongRequestDto(
                "Default Song" + UUID.randomUUID(),
                LocalDateTime.now().toInstant(ZoneOffset.UTC),
                0L,
                SongLanguageDto.OTHER
        ));

        album.addSongToAlbum(song);
        artist.addAlbum(album);
        return artistRepository.save(artist);
    }
}
