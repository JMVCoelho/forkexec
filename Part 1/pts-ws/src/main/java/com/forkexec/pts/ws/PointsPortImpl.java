package com.forkexec.pts.ws;

import javax.jws.WebService;
import com.forkexec.pts.domain.Points;

/**
 * This class implements the Web Service port type (interface). The annotations
 * below "map" the Java class to the WSDL definitions.
 */
@WebService(endpointInterface = "com.forkexec.pts.ws.PointsPortType",
            wsdlLocation = "PointsService.wsdl",
            name = "PointsWebService",
            portName = "PointsPort",
            targetNamespace = "http://ws.pts.forkexec.com/",
            serviceName = "PointsService")

public class PointsPortImpl implements PointsPortType {

    /**
     * The Endpoint manager controls the Web Service instance during its whole
     * lifecycle.
     */
    private final PointsEndpointManager endpointManager;

    /** Constructor receives a reference to the endpoint manager. */
    public PointsPortImpl(final PointsEndpointManager endpointManager) {
	this.endpointManager = endpointManager;
    }

    // Main operations -------------------------------------------------------

    @Override
	public void activateUser(final String userEmail) throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
    try {   
      Points pts = Points.getInstance();
      pts.activateUser(userEmail);
    }
    catch (com.forkexec.pts.domain.EmailAlreadyExistsFault_Exception e) {
      throwEmailAlreadyExistsException(e);
    }
    catch (com.forkexec.pts.domain.InvalidEmailFault_Exception e) {
      throwInvalidEmailException(e);
    }
  }

    @Override
    public int pointsBalance(final String userEmail) throws InvalidEmailFault_Exception {
      try {   
			  Points pts = Points.getInstance();
			  return pts.pointsBalance(userEmail);
			}
			catch (com.forkexec.pts.domain.InvalidEmailFault_Exception e) {
			  throwInvalidEmailException(e);
			}
			return -1;
    }

    @Override
    public int addPoints(final String userEmail, final int pointsToAdd)
	    throws InvalidEmailFault_Exception, InvalidPointsFault_Exception {
	    try {   
	      Points pts = Points.getInstance();
	      return pts.addPoints(userEmail, pointsToAdd);
	    }
	    catch (com.forkexec.pts.domain.InvalidEmailFault_Exception e) {
	      throwInvalidEmailException(e);
	    }
	    catch (com.forkexec.pts.domain.InvalidPointsFault_Exception e) {
	      throwInvalidPointsException(e);
	    }
			return -1;
    }

    @Override
    public int spendPoints(final String userEmail, final int pointsToSpend)
	    throws InvalidEmailFault_Exception, InvalidPointsFault_Exception, NotEnoughBalanceFault_Exception {
	    try {   
	      Points pts = Points.getInstance();
	      return pts.spendPoints(userEmail, pointsToSpend);
	    }
	    catch (com.forkexec.pts.domain.InvalidEmailFault_Exception e) {
	      throwInvalidEmailException(e);
	    }
      catch (com.forkexec.pts.domain.InvalidPointsFault_Exception e) {
        throwInvalidPointsException(e);
      }
	    catch (com.forkexec.pts.domain.NotEnoughBalanceFault_Exception e) {
	      throwNotEnoughBalanceException(e);
	    }
			return -1;
    }

    // Control operations ----------------------------------------------------
    // TODO
    /** Diagnostic operation to check if service is running. */
    @Override
    public String ctrlPing(String inputMessage) {
	// If no input is received, return a default name.
	if (inputMessage == null || inputMessage.trim().length() == 0)
	    inputMessage = "friend";

	// If the park does not have a name, return a default.
	String wsName = endpointManager.getWsName();
	if (wsName == null || wsName.trim().length() == 0)
	    wsName = "Park";

	// Build a string with a message to return.
	final StringBuilder builder = new StringBuilder();
	builder.append("Hello ").append(inputMessage);
	builder.append(" from ").append(wsName);
	return builder.toString();
    }

    /** Return all variables to default values. */
    @Override
    public void ctrlClear() {
      Points pts = Points.getInstance();
			pts.reset();
    }

    /** Set variables with specific values. */
    @Override
    public void ctrlInit(final int startPoints) throws BadInitFault_Exception {
    	try{
        Points pts = Points.getInstance();
        pts.setInitialBalance(startPoints);
      } catch (com.forkexec.pts.domain.BadInitFault_Exception e) {
        throwBadInitException(e);
      }
    }

    // Exception helpers -----------------------------------------------------

    /** Helper to throw a new BadInit exception. */
    private void throwEmailAlreadyExistsException(com.forkexec.pts.domain.EmailAlreadyExistsFault_Exception e) throws EmailAlreadyExistsFault_Exception {
      final EmailAlreadyExistsFault faultInfo = new EmailAlreadyExistsFault();
      faultInfo.message = e.getMessage();
      throw new EmailAlreadyExistsFault_Exception(e.getMessage(), faultInfo);
    }

    private void throwInvalidEmailException(com.forkexec.pts.domain.InvalidEmailFault_Exception e) throws InvalidEmailFault_Exception {
      final InvalidEmailFault faultInfo = new InvalidEmailFault();
      faultInfo.message = e.getMessage();
      throw new InvalidEmailFault_Exception(e.getMessage(), faultInfo);
    }

    private void throwInvalidPointsException(com.forkexec.pts.domain.InvalidPointsFault_Exception e) throws InvalidPointsFault_Exception {
      final InvalidPointsFault faultInfo = new InvalidPointsFault();
      faultInfo.message = e.getMessage();
      throw new InvalidPointsFault_Exception(e.getMessage(), faultInfo);
    }

    private void throwNotEnoughBalanceException(com.forkexec.pts.domain.NotEnoughBalanceFault_Exception e) throws NotEnoughBalanceFault_Exception {
      final NotEnoughBalanceFault faultInfo = new NotEnoughBalanceFault();
      faultInfo.message = e.getMessage();
      throw new NotEnoughBalanceFault_Exception(e.getMessage(), faultInfo);
    }
		private void throwBadInitException(com.forkexec.pts.domain.BadInitFault_Exception e) throws BadInitFault_Exception{
			final BadInitFault faultInfo = new BadInitFault();
			faultInfo.message = e.getMessage();
			throw new BadInitFault_Exception(e.getMessage(), faultInfo);
		}
}
