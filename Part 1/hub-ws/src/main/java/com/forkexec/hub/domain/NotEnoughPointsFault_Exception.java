package com.forkexec.hub.domain;


public class NotEnoughPointsFault_Exception extends Exception {

    private String message;

    public NotEnoughPointsFault_Exception(String message) {
        super(message);
        this.message = message;
    }

    public NotEnoughPointsFault_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage(){
        return message;
    }
}
