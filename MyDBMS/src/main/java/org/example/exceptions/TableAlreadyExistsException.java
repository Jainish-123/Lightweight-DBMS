package org.example.exceptions;

public class TableAlreadyExistsException extends RuntimeException{
    public TableAlreadyExistsException(String exMsg)
    {
        super(exMsg);
    }
}
