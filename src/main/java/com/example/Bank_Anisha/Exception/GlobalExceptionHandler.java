package com.example.Bank_Anisha.Exception;

import com.example.Bank_Anisha.dto.API_Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Handle custom exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<API_Response<Object>> handleResourceNotFound(ResourceNotFoundException ex) {
        API_Response<Object> response = new API_Response<>(
                "error",
                ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<API_Response<Object>> handleGenericException(Exception ex) {
        API_Response<Object> response = new API_Response<>(
                "error",
                "Something went wrong: " + ex.getMessage(),
                null
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Optional: handle unknown URLs if needed
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<API_Response<Object>> handleNoHandlerFound(NoHandlerFoundException ex) {
        API_Response<Object> response = new API_Response<>(
                "error",
                "The URL you are trying to access does not exist. Please check your request.",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
