package com.forkexec.pts.ws.it;

import static org.junit.Assert.assertEquals;

import com.forkexec.pts.ws.BadInitFault_Exception;
import com.forkexec.pts.ws.EmailAlreadyExistsFault_Exception;
import com.forkexec.pts.ws.InvalidEmailFault_Exception;
import com.forkexec.pts.ws.InvalidPointsFault_Exception;
import com.forkexec.pts.ws.NotEnoughBalanceFault_Exception;

import org.junit.After;
import org.junit.Test;

/**
 * Class that tests Clear operation
 */
public class PointsBalanceIT extends BaseIT {

	@After
    public void tearDown() {
        client.ctrlClear();
    }

	@Test
	public void initBalance()
			throws InvalidEmailFault_Exception, BadInitFault_Exception, EmailAlreadyExistsFault_Exception {
		client.ctrlInit(0);
		client.activateUser(USER_EMAIL);
		assertEquals(0, client.pointsBalance(USER_EMAIL));
	}

	@Test
	public void addPointsBalance() throws InvalidEmailFault_Exception, EmailAlreadyExistsFault_Exception,
			BadInitFault_Exception, InvalidPointsFault_Exception {
		client.ctrlInit(0);
		client.activateUser(USER_EMAIL);
		client.addPoints(USER_EMAIL, 1);
		assertEquals(1, client.pointsBalance(USER_EMAIL));
	}

	@Test
	public void spendPointsBalance() throws InvalidEmailFault_Exception, BadInitFault_Exception,
			EmailAlreadyExistsFault_Exception, InvalidPointsFault_Exception, NotEnoughBalanceFault_Exception {
		client.ctrlInit(10);
		client.activateUser(USER_EMAIL);
		client.spendPoints(USER_EMAIL, 1);
		assertEquals(9, client.pointsBalance(USER_EMAIL));
	}

	@Test(expected = InvalidEmailFault_Exception.class)
	public void emailNotUsed() throws InvalidEmailFault_Exception, BadInitFault_Exception, EmailAlreadyExistsFault_Exception, InvalidPointsFault_Exception {
		client.ctrlInit(0);
		client.activateUser(USER_EMAIL);
		client.pointsBalance(USER_EMAIL+1);
	}
}
