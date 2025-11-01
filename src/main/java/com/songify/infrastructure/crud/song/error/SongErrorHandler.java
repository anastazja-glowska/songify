package com.songify.infrastructure.crud.song.error;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.songify.infrastructure.crud.song.controller.SongsRestController;
import com.songify.domain.crud.SongNotFoundException;

@RestControllerAdvice
@Log4j2
public class SongErrorHandler {

    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<ErrorSongResponseDto> handleException(SongNotFoundException ex){
        log.warn("Error accessing song", ex.getMessage());
        ErrorSongResponseDto errorSongResponseDto = new ErrorSongResponseDto(ex.getMessage(),
                HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(errorSongResponseDto);
    }
}
