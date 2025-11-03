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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/albums")
class AlbumController {

    private final SongifyCrudFasade songifyCrudFasade;

    @PostMapping
    ResponseEntity<AlbumDto> postAlbum(@RequestBody AlbumRequestDto dto) {
        AlbumDto albumDto = songifyCrudFasade.addAlbumWithSong(dto);
        return  ResponseEntity.ok(albumDto);
    }

    @GetMapping("{albumId}")
     ResponseEntity<AlbumWithArtistsAndSongsDto> getAlbumByIdWithArtistsAndSongs(@PathVariable Long albumId){
        AlbumWithArtistsAndSongsDto albumDto = songifyCrudFasade.findAlbumByIdWithArtistsAndSongs(albumId);
        return ResponseEntity.ok(albumDto);
    }

    @GetMapping
    ResponseEntity<AllAlbumsDto> getAllAlbums(){
        Set<AlbumDto> allAlbums = songifyCrudFasade.findAllAlbums();
        return  ResponseEntity.ok(new AllAlbumsDto(allAlbums));

    }

    @PutMapping("/{albumId}/songs/{songId}")
    ResponseEntity<AlbumWithSongsResponseDto> addSongToAlbum(@PathVariable Long albumId, @PathVariable Long songId){
        AlbumWithSongsResponseDto albumWithSongsResponseDto = songifyCrudFasade.addSongToAlbum(songId, albumId);
        return  ResponseEntity.ok(albumWithSongsResponseDto);
    }
}
