package com.denizcanbagdatlioglu.self.config;

import com.denizcanbagdatlioglu.self.common.exception.NoEntityFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoEntityFoundException.class)
    public ResponseEntity<Void> handleNoEntityFoundException(NoEntityFoundException e){
        return ResponseEntity.notFound().build();
    }

}
