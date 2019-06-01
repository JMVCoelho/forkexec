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
public class SpendPointsIT extends BaseIT {
	@After
    public void tearDown() {
        client.ctrlClear();
    }

	@Test
	public void success() throws InvalidEmailFault_Exception, InvalidPointsFault_Exception,
			NotEnoughBalanceFault_Exception, EmailAlreadyExistsFault_Exception, BadInitFault_Exception {
		client.ctrlInit(10);
		client.activateUser(USER_EMAIL);
		client.spendPoints(USER_EMAIL, 1);
		assertEquals(9, client.pointsBalance(USER_EMAIL));
	}

	public void minBalance() throws InvalidEmailFault_Exception, InvalidPointsFault_Exception, NotEnoughBalanceFault_Exception, BadInitFault_Exception, EmailAlreadyExistsFault_Exception {
		client.ctrlInit(10);
		client.activateUser(USER_EMAIL);
		client.spendPoints(USER_EMAIL, 10);
		assertEquals(0, client.pointsBalance(USER_EMAIL));
	}

	@Test(expected = NotEnoughBalanceFault_Exception.class)
	public void intCapped() throws InvalidEmailFault_Exception, InvalidPointsFault_Exception, NotEnoughBalanceFault_Exception, BadInitFault_Exception, EmailAlreadyExistsFault_Exception {
		client.ctrlInit(0);
		client.activateUser(USER_EMAIL);
		client.spendPoints(USER_EMAIL, 1);
	}

	@Test(expected = InvalidPointsFault_Exception.class)
	public void notPositiveInt() throws InvalidEmailFault_Exception, InvalidPointsFault_Exception, NotEnoughBalanceFault_Exception, BadInitFault_Exception, EmailAlreadyExistsFault_Exception {
		client.ctrlInit(10);
		client.activateUser(USER_EMAIL);
		client.spendPoints(USER_EMAIL, 0);
	}

	@Test(expected = InvalidEmailFault_Exception.class)
	public void emailNotUsed() throws InvalidEmailFault_Exception, InvalidPointsFault_Exception, NotEnoughBalanceFault_Exception, EmailAlreadyExistsFault_Exception, BadInitFault_Exception {
		client.ctrlInit(20);
		client.activateUser(USER_EMAIL);
		client.spendPoints(USER_EMAIL+1, 10);
	}
}
