package com.cart.exceptions;

import com.cart.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDto> ResourceNotFoundExceptionHandler(ResourceNotFoundException resourceNotFoundException, WebRequest webRequest){
        return new ResponseEntity<>(new ExceptionDto(LocalDateTime.now(),resourceNotFoundException.getMessage(),webRequest.getDescription(false)),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> ExceptionHandler(Exception exception, WebRequest webRequest){
        return new ResponseEntity<>(new ExceptionDto(LocalDateTime.now(),exception.getMessage(),webRequest.getDescription(false)),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
