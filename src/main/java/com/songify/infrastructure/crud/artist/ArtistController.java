package com.songify.infrastructure.crud.artist;

import com.songify.domain.crud.SongifyCrudFasade;
import com.songify.domain.crud.dto.ArtistDto;
import com.songify.domain.crud.dto.ArtistRequestDto;
import com.songify.domain.crud.dto.SongDto;

import com.songify.infrastructure.crud.song.dto.request.CreateSongRequestDto;
import com.songify.infrastructure.crud.song.dto.response.CreateSongResponseDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping("/artists")
class ArtistController {

    private final SongifyCrudFasade songifyCrudFasade;

    @PostMapping
    public ResponseEntity<ArtistDto> postArtist(@RequestBody ArtistRequestDto dto) {
        ArtistDto artistDto = songifyCrudFasade.addArtist(dto);
        return  ResponseEntity.ok(artistDto);
    }

    @PostMapping("/album/song")
    public ResponseEntity<ArtistDto> postArtistWithDefaultAlbumAndSong(@RequestBody ArtistRequestDto dto) {
        ArtistDto artistDto = songifyCrudFasade.addArtistWithDefaultAlbumAndSong(dto);
        return  ResponseEntity.ok(artistDto);
    }

    @GetMapping
    public ResponseEntity<AllArtistsDto> getArtists(@RequestParam(required = false) Pageable pageable) {
        Set<ArtistDto> allArtists = songifyCrudFasade.findAllArtists(pageable);
        return  ResponseEntity.ok(new AllArtistsDto(allArtists));
    }

    @DeleteMapping("/{artistId}")
    public ResponseEntity<String> deleteArtistWithAlbumsAndSongs(@PathVariable Long artistId) {
        songifyCrudFasade.deleteArtistByIdWithAlbumsAndSongs(artistId);
        return ResponseEntity.ok("Artist with id " + artistId + " and all related albums and songs have been deleted.");
    }

    @PutMapping("/{artistId}/{albumId}")
    public ResponseEntity<String> addArtistToAlbum(@PathVariable Long artistId, @PathVariable Long albumId) {
        songifyCrudFasade.addArtistToAlbum(artistId, albumId);
        return ResponseEntity.ok("Album with id " + albumId + " has been added to Artist with id " + artistId + ".");
    }

    @PatchMapping("/{artistId}")
    public ResponseEntity<ArtistDto> updateArtistNameById(@PathVariable Long artistId,
                                                          @RequestBody @Valid ArtistUpdateRequestDto dto) {
        ArtistDto updatedArtist = songifyCrudFasade.updateArtistNameById(artistId, dto.newArtistName());
        return ResponseEntity.ok(updatedArtist);
    }
}
