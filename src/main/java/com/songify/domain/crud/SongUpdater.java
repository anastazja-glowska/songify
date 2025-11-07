package com.songify.domain.crud;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SongUpdater {


    private  final SongRetrierver songRetrierver;
    private final SongRepository songRepository;




     void updateById(Long id, Song newSong) {
        songRetrierver.findSongById(id);
        songRepository.updateById(id, newSong);

    }

    Song partiallyUpdateById(Long id, Song updatedSong) {
        Song songFromDataBase = songRetrierver.findSongById(id);
        if(updatedSong.getName() != null) {
            songFromDataBase.setName(updatedSong.getName());
            log.info("New song name: " + updatedSong.getName());
        }



        return songFromDataBase;
    }

    List<Song> findAllSongsByGenreId(Long genreId) {
        return songRepository.findAllSongsByGenreId(genreId);
    }

    void updateGenreId(Long id) {
        songRepository.updateSongsGenreId(id);
    }
}



