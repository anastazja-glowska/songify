package com.songify.infrastructure.crud.album;


import com.songify.domain.crud.AlbumNotFoundEcxeption;
import com.songify.domain.crud.SongNotFoundException;
import com.songify.infrastructure.crud.song.error.ErrorSongResponseDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
class AlbumErrorHandler {


    @ExceptionHandler(AlbumNotFoundEcxeption.class)
    public ResponseEntity<ErrorAlbumResponseDto> handleException(AlbumNotFoundEcxeption ex){
        log.warn("Error accessing album", ex.getMessage());
        ErrorAlbumResponseDto errorSongResponseDto = new ErrorAlbumResponseDto(ex.getMessage(),
                HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorSongResponseDto);
    }
}
