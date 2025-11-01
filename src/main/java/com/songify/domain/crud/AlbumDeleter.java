package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@AllArgsConstructor

class AlbumDeleter {

    private final AlbumRepository albumRepository;

    void deleteAllAblumsByIds(Set<Long> albumsIdsToDelete) {

        albumRepository.deleteByIdIn(albumsIdsToDelete);
    }

    void deleteAlbumById(Long albumId) {

        albumRepository.deleteById(albumId);
    }

//    void deleteAllSongsByIds(Set<Long> songsIdsToDelete) {
//
//        albumRepository.deleteSongsByIdIn(songsIdsToDelete);
//    }
}
