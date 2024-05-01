package com.product.exceptions;

import com.product.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    //    @ExceptionHandler({ResourceNotFoundException.class})
//    public ResponseEntity<ExceptionDto> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
//        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
//        return new ResponseEntity<>(exceptionDto, HttpStatus.NOT_FOUND);
//    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ExceptionDto exceptionDto = new ExceptionDto(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionDto);
    }
}
