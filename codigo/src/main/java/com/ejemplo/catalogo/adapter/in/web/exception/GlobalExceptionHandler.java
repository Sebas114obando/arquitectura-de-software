package com.ejemplo.catalogo.adapter.in.web.exception;

import com.ejemplo.catalogo.domain.exception.ProductNotFoundException;
import com.ejemplo.catalogo.domain.exception.ValidationException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ProductNotFoundException -> 404 Not Found
    @ExceptionHandler(ProductNotFoundException.class)
    public ProblemDetail handleNotFound(ProductNotFoundException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Producto no encontrado");
        return problem;
    }

    // ValidationException -> 400 Bad Request
    @ExceptionHandler(ValidationException.class)
    public ProblemDetail handleBusinessValidation(ValidationException ex) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, ex.getMessage());
        problem.setTitle("Error de validación de negocio");
        return problem;
    }

    // @Valid failure -> 400 Bad Request with each field's error
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidation(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new LinkedHashMap<>();
        for (var error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST, "Los datos enviados no son válidos");
        problem.setTitle("Error de validación");
        problem.setProperty("errors", errors);
        return problem;
    }
}

