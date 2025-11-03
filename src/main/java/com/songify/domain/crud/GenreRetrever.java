package com.songify.domain.crud;

import com.songify.domain.crud.dto.GenreDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
class GenreRetrever {

    private final GenreRepository genreRepository;

    Genre findGenreById(Long genreId){
        return genreRepository.findById(genreId).orElseThrow(() -> new GenreNotFoundException("Genre not found"));
    }


    Set<GenreDto> findAllGenres() {
        Set<Genre> genres = genreRepository.findAll();
        return genres.stream().map(genre -> new GenreDto(genre.getId(), genre.getName())).collect(Collectors.toSet());
    }
}
