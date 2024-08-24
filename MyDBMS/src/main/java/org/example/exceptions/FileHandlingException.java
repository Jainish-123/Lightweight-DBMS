package org.example.exceptions;

public class FileHandlingException extends RuntimeException{
    public FileHandlingException(String exMsg)
    {
        super(exMsg);
    }
}
