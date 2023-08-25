package com.ecureuill.ada.avanade.orderapi.exceptions;

public class InsufficientStockException extends Exception {
    public InsufficientStockException(String productName) {
        super("Not enough stock for " + productName);
    }
}
    

