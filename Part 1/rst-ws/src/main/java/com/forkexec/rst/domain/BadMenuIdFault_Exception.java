package com.forkexec.rst.domain;


public class BadMenuIdFault_Exception extends Exception {
    
    private static final long serialVersionUID = 3533282458977643470L;
    private String message;

    public BadMenuIdFault_Exception(String message) {
        super(message);
        this.message = message;
    }

    public BadMenuIdFault_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMessage(){
        return message;
    }
}