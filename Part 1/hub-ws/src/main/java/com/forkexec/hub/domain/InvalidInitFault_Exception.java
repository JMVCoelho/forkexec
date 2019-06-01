package com.forkexec.hub.domain;


public class InvalidInitFault_Exception extends Exception {

    private String message;

    public InvalidInitFault_Exception(String message) {
        super(message);
        this.message = message;
    }

    public InvalidInitFault_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage(){
        return message;
    }
}
