package com.songify.domain.crud;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Log4j2
@Transactional
class ArtistDeleter {

    private final ArtistRepository artistRepository;
    private final AlbumRetriever albumRetriever;
    private final ArtistRetriever artistRetriever;
    private final SongDeleter songDeleter;
    private final AlbumDeleter albumDeleter;
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;


    void deleteArtistByIdWithAlbumsAndSongs(Long artistId) {
        Artist artist = artistRetriever.findById(artistId);
        Set<Album> artistAlbums = albumRetriever.findAlbumsByArtistId(artistId);
        if (artistAlbums.isEmpty()) {
            log.info("No albums found with id {} ", artistId);
            artistRepository.deleteById(artistId);
            return;
        }


        Set<Album> albumsWithOneArtist = artistAlbums.stream()
                .filter(album -> album.getArtists().size() == 1)
                .collect(Collectors.toSet());


        log.info("Albums with only one artist to delete: {} ", albumsWithOneArtist.size());


        Set<Long> allSongsIdsFromAllAlbumsWhereWasOnlyOneArtist = albumsWithOneArtist.stream()
                .flatMap(album -> album.getSongs().stream())
                .map(song -> song.getId())
                .collect(Collectors.toSet());

        log.info("Songs to delete: {} ", allSongsIdsFromAllAlbumsWhereWasOnlyOneArtist.size());

        songDeleter.deleteAllSongsByIds(allSongsIdsFromAllAlbumsWhereWasOnlyOneArtist);

        Set<Long> albumsIdsToDelete = albumsWithOneArtist.stream()
                .map(Album::getId)
                .collect(Collectors.toSet());

        albumDeleter.deleteAllAblumsByIds(albumsIdsToDelete);


        artistAlbums.stream()
                .filter(album -> album.getArtists().size() >= 2)
                .forEach(album -> album.removeArtist(artist));


        artistRepository.deleteById(artistId);


    }
}


