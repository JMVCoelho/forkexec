package com.forkexec.hub.domain;


public class InvalidMoneyFault_Exception extends Exception {

    private String message;

    public InvalidMoneyFault_Exception(String message) {
        super(message);
        this.message = message;
    }

    public InvalidMoneyFault_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage(){
        return message;
    }
}
