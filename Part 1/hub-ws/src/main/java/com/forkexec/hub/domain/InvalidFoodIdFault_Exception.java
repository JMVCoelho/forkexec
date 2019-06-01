package com.forkexec.hub.domain;


public class InvalidFoodIdFault_Exception extends Exception {

    private String message;

    public InvalidFoodIdFault_Exception(String message) {
        super(message);
        this.message = message;
    }

    public InvalidFoodIdFault_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage(){
        return message;
    }
}
