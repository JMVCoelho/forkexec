package com.forkexec.hub.domain;


public class EmptyCartFault_Exception extends Exception {

    private String message;

    public EmptyCartFault_Exception(String message) {
        super(message);
        this.message = message;
    }

    public EmptyCartFault_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage(){
        return message;
    }
}
