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




    public void deleteArtistByIdWithAlbumsAndSongs(Long artistId) {
        Artist artist = artistRetriever.findById(artistId);
        Set<Album> artistAlbums = albumRetriever.findAlbumsByArtistId(artistId);

        if (artistAlbums.isEmpty()) {
            log.info("No albums found for artist with id {}", artistId);
            artistRepository.deleteById(artistId);
            return;
        }


        for (Album album : artistAlbums) {
            album.removeArtist(artist);
            albumRepository.save(album);
        }




        Set<Album> updatedAlbums = albumRetriever.findAlbumsByArtistId(artistId);

        log.info("Updated albums count after removing artist: {}", updatedAlbums.size());

        List<Long> artistIds = updatedAlbums.stream()
                .flatMap(album -> album.getArtists().stream())
                .map(Artist::getId)
                .collect(Collectors.toList());

        log.info("artist ids in updated albums: {}", artistIds);


        for (Album album : updatedAlbums) {
            log.info("Deleting album {}", album.getId());
        }


        Set<Album> orphanedAlbums = updatedAlbums.stream()
                .filter(album -> album.getArtists().isEmpty())
                .collect(Collectors.toSet());

        log.info("Albums with no artists to delete: {}", orphanedAlbums.size());


        Set<Long> songIdsToDelete = orphanedAlbums.stream()
                .flatMap(album -> album.getSongs().stream())
                .map(Song::getId)
                .collect(Collectors.toSet());

        log.info("Songs to delete: {}", songIdsToDelete.size());


        Set<Long> albumIdsToDelete = orphanedAlbums.stream()
                .map(Album::getId)
                .collect(Collectors.toSet());


        songRepository.deleteSongsByIds(songIdsToDelete);
        albumRepository.deleteAlbumsByIds(albumIdsToDelete);

        artistRepository.deleteById(artistId);
    }


//    void deleteArtistByIdWithAlbumsAndSongs(Long artistId) {
//        Artist artist = artistRetriever.findById(artistId);
//        Set<Album> artistAlbums = albumRetriever.findAlbumsByArtistId(artistId);
//        if(artistAlbums.isEmpty()){
//            log.info("No albums found with id {} ", artistId);
//            artistRepository.deleteById(artistId);
//            return;
//        }
//
//        artistAlbums.stream()
//                .filter(album -> album.getArtists().size() >= 2)
//                .forEach(album -> album.removeArtist(artist));
//
//        Set<Album> albumsWithOneArtist = artistAlbums.stream()
//                .filter(album -> album.getArtists().size() == 1)
//                .collect(Collectors.toSet());
//
////        artistRepository.removeAlbumFromArtist(artistId, );
////
////        artistAlbums.forEach(album -> {
////            album.removeArtist(artist);
////            albumRepository.save(album);
////        });
////
////        entityManager.flush();
//
//
////        Set<Album> albumsWithOneArtist = artistAlbums.stream()
////                .filter(album -> album.getArtists().isEmpty())
////                .collect(Collectors.toSet());
//
//        log.info("Albums with only one artist to delete: {} ", albumsWithOneArtist.size());
//
//
//        Set<Long> allSongsIdsFromAllAlbumsWhereWasOnlyOneArtist = albumsWithOneArtist.stream()
//                .flatMap(album -> album.getSongs().stream())
//                .map(song -> song.getId())
//                .collect(Collectors.toSet());
//
//        log.info("Songs to delete: {} ", allSongsIdsFromAllAlbumsWhereWasOnlyOneArtist.size());
//
//        songDeleter.deleteAllSongsByIds( allSongsIdsFromAllAlbumsWhereWasOnlyOneArtist);
//
//        Set<Long> albumsIdsToDelete = albumsWithOneArtist.stream()
//                .map(Album::getId)
//                .collect(Collectors.toSet());
//
//        albumDeleter.deleteAllAblumsByIds(albumsIdsToDelete);
//
//        albumsWithOneArtist.stream()
//                        .forEach(album -> albumDeleter.deleteAlbumById(album.getId()));
//
//        artistRepository.deleteById(artistId);
//
//
//    }
}
