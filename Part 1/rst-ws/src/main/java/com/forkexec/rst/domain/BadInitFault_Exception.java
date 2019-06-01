package com.forkexec.rst.domain;


public class BadInitFault_Exception extends Exception {
    
    private static final long serialVersionUID = 2599482502704489741L;
    private String message;

    public BadInitFault_Exception(String message) {
        super(message);
        this.message = message;
    }

    public BadInitFault_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage(){
        return message;
    }
}