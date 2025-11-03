package com.songify.domain.crud;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
class GenreAssigner {

    private  final GenreRetrever genreRetrever;
    private final SongRetrierver songRetrierver;


    void assignDefultGenreToSong(Long songId) {

        Song song = songRetrierver.findSongById(songId);
        Genre genre = genreRetrever.findGenreById(1L);
        song.setGenre(genre);

    }

    void assignGenreToSong(Long songId, Long genreId) {
        Song song = songRetrierver.findSongById(songId);
        Genre genre = genreRetrever.findGenreById(genreId);
        song.setGenre(genre);

    }
}
