package com.ecureuill.ada.avanade.orderapi.infra;

public class NotFoundException extends Exception {

    public NotFoundException(String query) {
        super("No row returned for query:" + query);
    }
    
}
