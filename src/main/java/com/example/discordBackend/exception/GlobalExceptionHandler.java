package com.example.discordBackend.exception;

import com.example.discordBackend.dtos.ApiResponse;
import com.example.discordBackend.dtos.ErrorInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    // handle specific as well as global exceptions

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        TreeMap<String, String> errors = new TreeMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        var err = errors.pollFirstEntry();
        return new ResponseEntity<>(new ApiResponse(false, null, new ErrorInfo(
                "", String.format("%s %s", err.getKey(), err.getValue())
        )), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DiscordException.class)
    public ResponseEntity<ApiResponse> handleDiscordApiException(
            DiscordException exception,
            WebRequest webRequest
    ){
        ApiResponse apiResponse = new ApiResponse(
                false,
                null,
                new ErrorInfo("", exception.getMessage())
        );
        return new ResponseEntity<>(apiResponse, exception.getStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleAccessDeniedException(
            AccessDeniedException exception,
            WebRequest webRequest
    ){
        ApiResponse apiResponse = new ApiResponse(
                false,
                null,
                new ErrorInfo("", exception.getMessage())
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleGlobalException(
            Exception exception,
            WebRequest webRequest
    ){
        ApiResponse apiResponse = new ApiResponse(
                false,
                null,
                new ErrorInfo("", exception.getMessage())
        );

        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
