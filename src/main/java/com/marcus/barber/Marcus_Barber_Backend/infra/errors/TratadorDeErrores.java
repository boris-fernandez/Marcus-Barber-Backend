package com.marcus.barber.Marcus_Barber_Backend.infra.errors;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.marcus.barber.Marcus_Barber_Backend.exception.CredencialesInvalidasException;
import com.marcus.barber.Marcus_Barber_Backend.exception.ValidacionException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErrores {

    @ExceptionHandler(EntityNotFoundException.class)
    public final ResponseEntity<?> tratarError404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<?> tratarError400(MethodArgumentNotValidException e){
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    @ExceptionHandler(ValidacionException.class)
    public ResponseEntity<?> tratarErrorDeValidacion(ValidacionException e){
        return ResponseEntity.badRequest().body(new ErrorGeneral(e.getMessage()));
    }

    @ExceptionHandler(CredencialesInvalidasException.class)
    public ResponseEntity<?> tratarErrorCredencialesIncorrectas(CredencialesInvalidasException e){
        return ResponseEntity.badRequest().body(new ErrorGeneral(e.getMessage()));
    }

}
