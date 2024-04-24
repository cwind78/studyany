package com.example.studyany.config.exception;

import com.example.studyany.vo.common.Rslt;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Rslt handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        StringBuilder errorMessage = new StringBuilder();
        result.getAllErrors().forEach(error -> {
            errorMessage.append(error.getDefaultMessage()).append(", ");
        });
        return Rslt.builder().cd("E").msg(errorMessage.toString()).build();
    }
}
