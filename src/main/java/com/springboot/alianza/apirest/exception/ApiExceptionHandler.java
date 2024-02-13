package com.springboot.alianza.apirest.exception;


import com.springboot.alianza.apirest.dto.GeneralResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {


    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public GeneralResponse<String> handlerRequestException(NotFoundException e) {
        return new GeneralResponse<>(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralResponse<String> handlerRequestException(MethodArgumentNotValidException e) {
        String errorMsg = e.getBindingResult().getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).findFirst().orElse(e.getMessage());
        return new GeneralResponse<>(HttpStatus.BAD_REQUEST, errorMsg);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GeneralResponse<String> handlerRequestException(BadRequestException e) {
        return new GeneralResponse<>(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(GeneralException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GeneralResponse<String> handlerRequestException(GeneralException e) {
        return new GeneralResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }
}