package com.forkexec.hub.domain;


public class InvalidFoodQuantityFault_Exception extends Exception {

    private String message;

    public InvalidFoodQuantityFault_Exception(String message) {
        super(message);
        this.message = message;
    }

    public InvalidFoodQuantityFault_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage(){
        return message;
    }
}
