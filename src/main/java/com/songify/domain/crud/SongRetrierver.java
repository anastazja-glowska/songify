package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.SongDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Service
@Log4j2
@AllArgsConstructor(access = AccessLevel.PACKAGE)
 class SongRetrierver {

    private final SongRepository songRepository;




     List< SongDto> findAll(Pageable pageable) {
        log.info("Retriving all songs");
//        return songRepository.findAll(pageable).stream()
//                .map(s -> SongDto.builder()
//                        .id(s.getId())
//                        .name(s.getName())
//                        .build())
//                .collect(Collectors.toList());

         List<SongDto> songDtos = new ArrayList<>();
         List<Song> songs = songRepository.findAll(pageable);

         for(Song s : songs){
             Genre genre = s.getGenre();
             GenreDto genreDto = new GenreDto(genre.getId(), genre.getName());

             SongDto songDto = SongDto.builder()
                     .id(s.getId())
                     .name(s.getName())
                     .genre(genreDto)
                        .build();

                songDtos.add(songDto);
         }

         return  songDtos;

     }


     SongDto findSongDtoById(Long id) {
        return songRepository.findById(id).map(song -> SongDto.builder()
                 .id(song.getId())
                 .name(song.getName())
                 .build())
                .orElseThrow(() -> new SongNotFoundException(
                        "Song not found"));
    }

    Song findSongById(Long id) {
        return songRepository.findById(id)
                .orElseThrow(() -> new SongNotFoundException("Song not found"));
    }




//     Song compareSongs() {
//        final List<Song> songs = new ArrayList<>();
//        Song song = songRepository.findById(12L)
//                .orElseThrow(() -> new SongNotFoundException("Song not found"));
//
//        Song song1 = songRepository.findById(14L)
//                .orElseThrow(() -> new SongNotFoundException("Song not found"));
//
////        log.info("adding new song " + song.hashCode());
//        songs.add(song);
//        songs.add(song1);
//
////        for(Song s : songs) {
////            log.info("s hashcode " + s.hashCode()  );
////        }
//
//        log.info(songs.get(0).equals(songs.get(1)));
//        return song;
//    }
}
