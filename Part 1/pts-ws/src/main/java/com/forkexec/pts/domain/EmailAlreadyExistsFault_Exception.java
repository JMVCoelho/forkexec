package com.forkexec.pts.domain;


public class EmailAlreadyExistsFault_Exception extends Exception {
    
    private static final long serialVersionUID = 6086646376809610074L;
    private String message;

    public EmailAlreadyExistsFault_Exception(String message) {
        super(message);
        this.message = message;
    }

    public EmailAlreadyExistsFault_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage(){
        return message;
    }
}