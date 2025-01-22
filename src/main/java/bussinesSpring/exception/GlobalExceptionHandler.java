package bussinesSpring.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de validación de DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();

        List<String> errorMessages = bindingResult.getAllErrors().stream()
                .map(error -> ((FieldError) error).getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());

        ResponseWrapper<Object> response = new ResponseWrapper<>(null, String.join(", ", errorMessages), 400);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Manejo de excepciones generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseWrapper<Object>> handleException(Exception ex) {
        ResponseWrapper<Object> response = new ResponseWrapper<>(null, ex.getMessage(), 500);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Manejo de excepciones personalizadas (Ejemplo: Product not found)
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleProductNotFound(ProductNotFoundException ex) {
        ResponseWrapper<Object> response = new ResponseWrapper<>(null, ex.getMessage(), 404);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Manejo de excepciones de base de datos (Ejemplo: ConstraintViolationException)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleDatabaseExceptions(DataIntegrityViolationException ex) {
        ResponseWrapper<Object> response = new ResponseWrapper<>(null, "Database error: " + ex.getMessage(), 400);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Manejo de excepciones de violaciones de restricciones
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ResponseWrapper<Object>> handleConstraintViolation(ConstraintViolationException ex) {
        ResponseWrapper<Object> response = new ResponseWrapper<>(null, ex.getMessage(), 400);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}