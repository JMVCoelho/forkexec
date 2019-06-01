package com.forkexec.pts.ws;

import javax.jws.WebService;
import com.forkexec.pts.ws.Account;
import com.forkexec.pts.domain.Points;
import com.forkexec.pts.domain.Balance;

/**
 * This class implements the Web Service port type (interface). The annotations
 * below "map" the Java class to the WSDL definitions.
 */
@WebService(endpointInterface = "com.forkexec.pts.ws.PointsPortType", wsdlLocation = "PointsService.wsdl", name = "PointsWebService", portName = "PointsPort", targetNamespace = "http://ws.pts.forkexec.com/", serviceName = "PointsService")
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

	public void write(Account account) {
		Points pts = Points.getInstance();
		pts.write(account.getUserEmail(), new Balance(account.getTag(), account.getPoints()));
	}

	public Account read(String userEmail) {
		Points pts = Points.getInstance();
		Balance balance = pts.read(userEmail);
		Account acc = createAccount(userEmail, balance.getTag(), balance.getPoints());
		return acc;
	}

	public boolean doesUserExist(String userEmail) {
		Points pts = Points.getInstance();
		return pts.doesUserExist(userEmail);
	}

	// Control operations ----------------------------------------------------
	/** Diagnostic operation to check if service is running. */
	public String ctrlPing(String inputMessage) {
		// If no input is received, return a default name.
		if (inputMessage == null || inputMessage.trim().length() == 0)
			inputMessage = "friend";

		// If the service does not have a name, return a default.
		String wsName = endpointManager.getWsName();
		if (wsName == null || wsName.trim().length() == 0)
			wsName = PointsApp.class.getSimpleName();

		// Build a string with a message to return.
		final StringBuilder builder = new StringBuilder();
		builder.append("Hello ").append(inputMessage);
		builder.append(" from ").append(wsName);
		return builder.toString();
	}

	/** Return all variables to default values. */
	public void ctrlClear() {
		Points pts = Points.getInstance();
		pts.reset();
	}

	public void ctrlInit(int initialPoints) {
		Points pts = Points.getInstance();
		pts.setInitialPoints(initialPoints);
	}

	// Construction helpers -----------------------------------------------------
	private Account createAccount(final String userEmail, final long tag, final int points) {
		Account acc = new Account();
		acc.setPoints(points);
		acc.setUserEmail(userEmail);
		acc.setTag(tag);
		return acc;
	}

}
