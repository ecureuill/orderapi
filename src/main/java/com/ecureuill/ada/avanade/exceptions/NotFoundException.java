package com.ecureuill.ada.avanade.exceptions;

public class NotFoundException extends Exception {

    public NotFoundException(String query) {
        super("No row returned for query:" + query);
    }
    
}
