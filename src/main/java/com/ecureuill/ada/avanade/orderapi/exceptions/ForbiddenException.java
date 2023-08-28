package com.ecureuill.ada.avanade.orderapi.exceptions;

public class ForbiddenException extends Exception {

    public ForbiddenException() {
        super("Not allowed");
    }
    
}
