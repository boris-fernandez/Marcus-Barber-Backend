package com.marcus.barber.Marcus_Barber_Backend.exception;

public class CredencialesInvalidasException extends RuntimeException{

    public CredencialesInvalidasException(String mensaje){
        super(mensaje);
    }
}
