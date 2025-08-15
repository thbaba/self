package com.denizcanbagdatlioglu.self.config;

import com.denizcanbagdatlioglu.self.common.exception.NoEntityFoundException;
import com.denizcanbagdatlioglu.self.user.exception.UserRegistrationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoEntityFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNoEntityFoundException(NoEntityFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionResponse.of(e.getMessage()));
    }

    @ExceptionHandler(UserRegistrationException.class)
    public ResponseEntity<ExceptionResponse> handleUserRegistrationException(UserRegistrationException e){
        return ResponseEntity.internalServerError().body(ExceptionResponse.of(e.getMessage()));
    }

}
