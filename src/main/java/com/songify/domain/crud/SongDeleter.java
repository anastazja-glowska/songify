package com.songify.domain.crud;

import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Log4j2
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
class SongDeleter {

    private  final SongRepository songRepository;
    private  final SongRetrierver songRetrierver;
    private  final GenreDeleter genreDeleter;
    private  final SongUpdater songUpdater;


     void deleteSongById(Long id) {
        songRetrierver.findSongById(id);
        log.info("delete by id " + id);
        songRepository.deleteById(id);
    }

    void deleteSongAndGenreById(Long id) {
        Song song = songRetrierver.findSongById(id);
        log.info("delete by id " + id);
        Long genreId = song.getGenre().getId();
        deleteSongById(id);
        List<Song> songsWithGenreId = songUpdater.findAllSongsByGenreId(genreId);
        for(Song s : songsWithGenreId){
            songUpdater.updateGenreId(s.getId());
        }
        genreDeleter.deleteGenreById(genreId);

    }

    void deleteAllSongsByIds(Set<Long> allSongs) {
        songRepository.deleteByIdIn(allSongs);
    }
}
