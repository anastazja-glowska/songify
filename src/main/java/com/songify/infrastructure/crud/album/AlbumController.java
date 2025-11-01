package com.songify.infrastructure.crud.album;

import com.songify.domain.crud.SongifyCrudFasade;
import com.songify.domain.crud.dto.AlbumDto;


import com.songify.domain.crud.dto.AlbumRequestDto;
import com.songify.domain.crud.dto.AlbumWithArtistsAndSongsDto;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/albums")
class AlbumController {

    private final SongifyCrudFasade songifyCrudFasade;

    @PostMapping
    public ResponseEntity<AlbumDto> postAlbum(@RequestBody AlbumRequestDto dto) {
        AlbumDto albumDto = songifyCrudFasade.addAlbumWithSong(dto);
        return  ResponseEntity.ok(albumDto);
    }

    @GetMapping("{albumId}")
    public ResponseEntity<AlbumWithArtistsAndSongsDto> getAlbumByIdWithArtistsAndSongs(@PathVariable Long albumId){
        AlbumWithArtistsAndSongsDto albumDto = songifyCrudFasade.findAlbumByIdWithArtistsAndSongs(albumId);
        return ResponseEntity.ok(albumDto);
    }
}
