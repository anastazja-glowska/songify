package com.songify.infrastructure.applicationValidation;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.songify.infrastructure.crud.song.controller.SongsRestController;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiValidationErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiValidationErrorResonseDto handleValidationException(MethodArgumentNotValidException exception) {
        List<String> message = getErrorsFromException(exception);
        return new ApiValidationErrorResonseDto(message,  HttpStatus.BAD_REQUEST);
    }

    private List<String> getErrorsFromException(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
