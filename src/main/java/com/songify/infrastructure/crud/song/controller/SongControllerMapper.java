package com.songify.infrastructure.crud.song.controller;

import com.songify.infrastructure.crud.song.dto.request.UpdateSongRequestDto;
import com.songify.infrastructure.crud.song.dto.response.DeleteSongResponseDto;
import com.songify.infrastructure.crud.song.dto.response.GetSongResponseDto;
import com.songify.infrastructure.crud.song.dto.response.PartiallyUpdateSongResponseDto;
import com.songify.infrastructure.crud.song.dto.response.UpdateSongResponseDto;
import org.springframework.http.HttpStatus;

import com.songify.domain.crud.dto.SongDto;
import com.songify.infrastructure.crud.song.dto.request.CreateSongRequestDto;
import com.songify.infrastructure.crud.song.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.infrastructure.crud.song.dto.response.CreateSongResponseDto;
import com.songify.infrastructure.crud.song.dto.response.GetAllSongsResponseDto;
import com.songify.infrastructure.crud.song.dto.response.SongControllerResponseDto;

import java.util.List;

class SongControllerMapper {


    static SongDto mapFromCreateSongRequestDtoToSongDto(CreateSongRequestDto request) {
        return SongDto.builder()
                .name(request.songName())
                .build();
    }

    static SongDto mapFromUpdateSongRequestDtoToSongDto(UpdateSongRequestDto request) {
        return SongDto.builder()
                .name(request.songName())
                .build();
    }

    static CreateSongResponseDto mapFromSongDtoToCreateSongResponseDto(SongDto song) {
        CreateSongResponseDto responseDto = new CreateSongResponseDto(song);
        return responseDto;
    }

    static SongDto mapFromPartiallyUpdatedSongRequestDtoToSongDto(PartiallyUpdateSongRequestDto request) {
        return SongDto.builder()
                .name(request.songName())
                .build();
    }

    static DeleteSongResponseDto mapFromSongToDeleteSongResponseDto(Long id) {
        return new DeleteSongResponseDto("You deleted song with id " + id, HttpStatus.OK);
    }

    static UpdateSongResponseDto mapFromSongToUpdateSongResponseDto(SongDto songDto) {
        return new UpdateSongResponseDto(songDto.name(), "test");
    }

    static PartiallyUpdateSongResponseDto mapFromSongToPartiallyUpdateSongResponseDto(SongDto songDto) {
        return new PartiallyUpdateSongResponseDto(songDto);
    }

    static GetSongResponseDto mapFromSongToGetSongResponseDto(SongDto songDto) {
        return new GetSongResponseDto(songDto);
    }



    static GetAllSongsResponseDto mapFromSongsToGetAllSongsResponseDto(List<SongDto> songs) {
        List<SongControllerResponseDto> songDtos = songs.stream()
                .map(s -> SongControllerResponseDto.builder()
                        .id(s.id())
                        .name(s.name())
                        .build()).toList();

        return new GetAllSongsResponseDto(songDtos);

    }


}
