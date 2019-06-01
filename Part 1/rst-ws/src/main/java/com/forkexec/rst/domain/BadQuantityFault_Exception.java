package com.forkexec.rst.domain;


public class BadQuantityFault_Exception extends Exception {
    
    private static final long serialVersionUID = -2689895371547542199L;
    private String message;

    public BadQuantityFault_Exception(String message) {
        super(message);
        this.message = message;
    }

    public BadQuantityFault_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage(){
        return message;
    }
}