package com.forkexec.rst.domain;


public class InsufficientQuantityFault_Exception extends Exception {
    
    private static final long serialVersionUID = 7226502823577996130L;
    private String message;

    public InsufficientQuantityFault_Exception(String message) {
        super(message);
        this.message = message;
    }

    public InsufficientQuantityFault_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage(){
        return message;
    }
}