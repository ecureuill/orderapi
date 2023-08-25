package com.ecureuill.ada.avanade.orderapi.exceptions;

public class NotFoundException extends Exception {

    public NotFoundException(String query) {
        super("No row returned for query:" + query);
    }
    
}
