package com.songify.infrastructure.crud.genre;


import com.songify.domain.crud.SongifyCrudFasade;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.GenreDto;
import com.songify.domain.crud.dto.GenreRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/genres")
class GenreController {


    private final SongifyCrudFasade songifyCrudFasade;

    @PostMapping
    public ResponseEntity<GenreDto> postArtist(@RequestBody GenreRequestDto dto) {
        GenreDto genreDto = songifyCrudFasade.addGenre(dto);
        return  ResponseEntity.ok(genreDto);
    }
}
