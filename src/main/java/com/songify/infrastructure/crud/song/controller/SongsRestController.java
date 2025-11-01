package com.songify.infrastructure.crud.song.controller;


import com.songify.domain.crud.dto.SongRequestDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.songify.domain.crud.SongifyCrudFasade;
import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.song.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.crud.song.dto.request.UpdateSongRequestDto;

import com.songify.infrastructure.crud.song.dto.response.CreateSongResponseDto;
import com.songify.infrastructure.crud.song.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.crud.song.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.crud.song.dto.response.GetSongResponseDto;
import com.songify.infrastructure.crud.song.dto.response.PartiallyUpdateSongResponseDto;
import com.songify.infrastructure.crud.song.dto.response.UpdateSongResponseDto;


import java.util.List;

@RestController
@Log4j2
@RequestMapping("/songs")
@AllArgsConstructor
public class SongsRestController {

    private final SongifyCrudFasade songCrudFasade;

    @GetMapping
    public ResponseEntity<GetAllSongsResponseDto> getAllSongs(@RequestParam(required = false)  Pageable pageable) {
        List<SongDto> songDtos = songCrudFasade.findAllSongs(pageable);
        GetAllSongsResponseDto mapped = SongControllerMapper.mapFromSongsToGetAllSongsResponseDto(songDtos);

        return ResponseEntity.ok(mapped);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSongResponseDto> getSongById(@PathVariable Long id, @RequestHeader(required = false) String requestId) {
        log.info(requestId);
        SongDto song = songCrudFasade.findSongDtoById(id);
        GetSongResponseDto responseDto = SongControllerMapper.mapFromSongToGetSongResponseDto(song);
        return ResponseEntity.ok(responseDto);
    }


    @PostMapping
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid SongRequestDto request) {

        SongDto savedSong = songCrudFasade.addSong(request);
        CreateSongResponseDto createSongResponseDto = SongControllerMapper.mapFromSongDtoToCreateSongResponseDto(savedSong);
        return  ResponseEntity.ok(createSongResponseDto);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Long id) {
        songCrudFasade.deleteSongById(id);
        DeleteSongResponseDto responseDto = SongControllerMapper.mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}/genre")
    public ResponseEntity<DeleteSongResponseDto> deleteSongAndGenre(@PathVariable Long id) {
        songCrudFasade.deleteSongAndGenreById(id);
        DeleteSongResponseDto responseDto = SongControllerMapper.mapFromSongToDeleteSongResponseDto(id);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteSongByIdUsingQueryParam(@RequestParam Long requestId) {
        songCrudFasade.deleteSongById(requestId);
        log.info("Deleted song with id: " + requestId);
        return ResponseEntity.ok("Song has been deleted with id " + requestId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Long id, @RequestBody
    @Valid UpdateSongRequestDto request) {

        SongDto songDto = SongControllerMapper.mapFromUpdateSongRequestDtoToSongDto(request);

        songCrudFasade.updateSongById(id, songDto);
        UpdateSongResponseDto responseDto = SongControllerMapper.mapFromSongToUpdateSongResponseDto( songDto);
        return ResponseEntity.ok(responseDto);


    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdate(@PathVariable Long id, @RequestBody
    PartiallyUpdateSongRequestDto request){


        SongDto updatedSong = SongControllerMapper.mapFromPartiallyUpdatedSongRequestDtoToSongDto(request);

        SongDto song = songCrudFasade.updateSongPartiallyById(id, updatedSong);
        PartiallyUpdateSongResponseDto responseDto = SongControllerMapper.mapFromSongToPartiallyUpdateSongResponseDto(song);
        return ResponseEntity.ok(responseDto);

    }

}
