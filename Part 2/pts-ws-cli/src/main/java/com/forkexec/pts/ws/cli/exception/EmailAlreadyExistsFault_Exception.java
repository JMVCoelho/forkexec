
package com.forkexec.pts.ws.cli.exception;

import javax.xml.ws.WebFault;

/**
 * This class was generated by the JAX-WS RI. JAX-WS RI 2.2.10 Generated source
 * version: 2.2
 * 
 */
@WebFault(name = "EmailAlreadyExistsFault", targetNamespace = "http://ws.pts.forkexec.com/")
public class EmailAlreadyExistsFault_Exception extends Exception {

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private EmailAlreadyExistsFault faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public EmailAlreadyExistsFault_Exception(String message, EmailAlreadyExistsFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public EmailAlreadyExistsFault_Exception(String message, EmailAlreadyExistsFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return returns fault bean: com.forkexec.pts.ws.EmailAlreadyExistsFault
     */
    public EmailAlreadyExistsFault getFaultInfo() {
        return faultInfo;
    }

}