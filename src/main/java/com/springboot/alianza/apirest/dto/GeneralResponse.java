package com.springboot.alianza.apirest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneralResponse<T> {

    private Integer code;
    private HttpStatus httpStatus;
    private String message;
    private T data;

    public GeneralResponse(HttpStatus httpStatus, Integer code, String message, T data) {
        this.message = message;
        this.code = code;
        this.httpStatus = httpStatus;
        this.data = data;
    }

    public GeneralResponse(HttpStatus httpStatus, Integer code, String message) {
        this.message = message;
        this.code = code;
        this.httpStatus = httpStatus;
    }

    public GeneralResponse(HttpStatus httpStatus, String message, T data) {
        this(httpStatus, httpStatus.value(), message, data);
    }

    public GeneralResponse(HttpStatus httpStatus, String message) {
        this(httpStatus, httpStatus.value(), message);
    }

    public GeneralResponse(String message) {
        this.message = message;
        this.httpStatus = HttpStatus.OK;
        this.code = this.httpStatus.value();
    }

    public GeneralResponse(T data, String message) {
        this(message);
        this.data = data;
    }

}