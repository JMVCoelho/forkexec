package com.forkexec.hub.domain;


public class InvalidUserIdFault_Exception extends Exception {

    private static final long serialVersionUID = -1365372362059501028L;
    private String message;

    public InvalidUserIdFault_Exception(String message) {
        super(message);
        this.message = message;
    }

    public InvalidUserIdFault_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage(){
        return message;
    }
}
