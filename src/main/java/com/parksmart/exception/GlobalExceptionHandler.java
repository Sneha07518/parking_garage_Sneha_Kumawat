package com.parksmart.exception;
import java.util.Map; import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import org.springframework.dao.DataIntegrityViolationException;
@RestControllerAdvice public class GlobalExceptionHandler {
    @ExceptionHandler(ApiException.class) ResponseEntity<?> api(ApiException e){return ResponseEntity.status(e.getStatus()).body(Map.of("error",Map.of("code",e.getCode(),"message",e.getMessage())));}
    @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<?> validation(MethodArgumentNotValidException e){return ResponseEntity.badRequest().body(Map.of("error",Map.of("code","VALIDATION_ERROR","message",e.getBindingResult().getFieldErrors().stream().findFirst().map(x->x.getField()+" is invalid").orElse("Invalid request"))));}
    @ExceptionHandler(DataIntegrityViolationException.class) ResponseEntity<?> conflict(DataIntegrityViolationException e){return ResponseEntity.status(409).body(Map.of("error",Map.of("code","CONFLICT","message","Parking spot or plate is already active")));}
    @ExceptionHandler(Exception.class) ResponseEntity<?> other(Exception e){return ResponseEntity.status(500).body(Map.of("error",Map.of("code","INTERNAL_ERROR","message","Unexpected server error")));}
}