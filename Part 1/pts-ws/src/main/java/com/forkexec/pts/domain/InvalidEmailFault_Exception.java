package com.forkexec.pts.domain;


public class InvalidEmailFault_Exception extends Exception {
    
    private static final long serialVersionUID = 6086646376809610074L;
    private String message;

    public InvalidEmailFault_Exception(String message) {
        super(message);
        this.message = message;
    }

    public InvalidEmailFault_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage(){
        return message;
    }
}