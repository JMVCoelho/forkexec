package com.forkexec.hub.domain;


public class InvalidCreditCardFault_Exception extends Exception {

    private String message;

    public InvalidCreditCardFault_Exception(String message) {
        super(message);
        this.message = message;
    }

    public InvalidCreditCardFault_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage(){
        return message;
    }
}
