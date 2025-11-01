package com.songify.infrastructure.applicationValidation;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ApiValidationErrorResonseDto(List<String> errors, HttpStatus status) {
}
