package com.songify.infrastructure.crud.genre;


import com.songify.domain.crud.SongifyCrudFasade;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/genres")
@Log4j2
class GenreController {


    private final SongifyCrudFasade songifyCrudFasade;

    @PostMapping
    ResponseEntity<GenreDto> postGenre(@RequestBody GenreRequestDto dto) {
        GenreDto genreDto = songifyCrudFasade.addGenre(dto);
        log.info(genreDto.name());
        return  ResponseEntity.ok(genreDto);
    }

    @GetMapping
    ResponseEntity<GetAllGenresResponseDto> getGenres(){
        Set<GenreDto> allGenresDto = songifyCrudFasade.retrieveGenres();
        return ResponseEntity.ok(new GetAllGenresResponseDto(allGenresDto));
    }

}
