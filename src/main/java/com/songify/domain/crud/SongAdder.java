package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.SongDto;
import com.songify.domain.crud.dto.SongLanguageDto;
import com.songify.domain.crud.dto.SongRequestDto;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;


@Log4j2
@Service
@Transactional
@AllArgsConstructor(access = AccessLevel.PACKAGE)
 class SongAdder {

    private final SongRepository songRepository;

     SongDto addSong(SongRequestDto songDto) {
        log.info(songDto);
         SongLanguageDto language = songDto.language();
         SongLanguage songLanguage = SongLanguage.valueOf(language.name());
         Song song = new Song(songDto.name(), songDto.releaseDate(), songDto.duration(), songLanguage);
            Song savedSong = songRepository.save(song);
         return new SongDto(savedSong.getId(), savedSong.getName(),
                 new GenreDto(savedSong.getGenre().getId(), savedSong.getGenre().getName()));
    }

    Song addSongAndGetEntity(SongRequestDto songDto) {
        log.info(songDto);
        SongLanguageDto language = songDto.language();
        SongLanguage songLanguage = SongLanguage.valueOf(language.name());
        Song song = new Song(songDto.name(), songDto.releaseDate(), songDto.duration(), songLanguage);
        return songRepository.save(song);

    }


}
