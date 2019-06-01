package com.forkexec.hub.domain;


public class InvalidTextFault_Exception extends Exception {

    private String message;

    public InvalidTextFault_Exception(String message) {
        super(message);
        this.message = message;
    }

    public InvalidTextFault_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage(){
        return message;
    }
}
