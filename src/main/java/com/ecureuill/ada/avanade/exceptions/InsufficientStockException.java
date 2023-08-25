package com.ecureuill.ada.avanade.exceptions;

public class InsufficientStockException extends Exception {
    public InsufficientStockException(String productName) {
        super("Not enough stock for " + productName);
    }
}
    

