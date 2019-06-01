package com.forkexec.pts.ws.it;

import static org.junit.Assert.assertEquals;

import com.forkexec.pts.ws.BadInitFault_Exception;
import com.forkexec.pts.ws.EmailAlreadyExistsFault_Exception;
import com.forkexec.pts.ws.InvalidEmailFault_Exception;
import com.forkexec.pts.ws.InvalidPointsFault_Exception;

import java.lang.Integer;

import org.junit.After;
import org.junit.Test;

/**
 * Class that tests Clear operation
 */
public class AddPointsIT extends BaseIT {

	@After
    public void tearDown() {
        client.ctrlClear();
    }

	@Test
	public void success() throws InvalidEmailFault_Exception, InvalidPointsFault_Exception,
			EmailAlreadyExistsFault_Exception, BadInitFault_Exception {
		client.ctrlInit(0);
		client.activateUser(USER_EMAIL);
		client.addPoints(USER_EMAIL, 1);
		assertEquals(1, client.pointsBalance(USER_EMAIL));
	}

	public void maxBalance() throws InvalidEmailFault_Exception, InvalidPointsFault_Exception, EmailAlreadyExistsFault_Exception, BadInitFault_Exception {
		client.ctrlInit(0);
		client.activateUser(USER_EMAIL);
		client.addPoints(USER_EMAIL, Integer.MAX_VALUE);
		assertEquals(Integer.MAX_VALUE, client.pointsBalance(USER_EMAIL));
	}

	@Test(expected = InvalidPointsFault_Exception.class)
	public void intCapped() throws InvalidEmailFault_Exception, InvalidPointsFault_Exception, BadInitFault_Exception,
			EmailAlreadyExistsFault_Exception {
		client.ctrlInit(Integer.MAX_VALUE);
		client.activateUser(USER_EMAIL);
		client.addPoints(USER_EMAIL, 1);
	}

	@Test(expected = InvalidPointsFault_Exception.class)
	public void notPositiveInt() throws InvalidEmailFault_Exception, InvalidPointsFault_Exception, BadInitFault_Exception, EmailAlreadyExistsFault_Exception {
		client.ctrlInit(10);
		client.activateUser(USER_EMAIL);
		client.addPoints(USER_EMAIL, 0);
	}

	@Test(expected = InvalidEmailFault_Exception.class)
	public void emailNotUsed() throws InvalidEmailFault_Exception, InvalidPointsFault_Exception, BadInitFault_Exception,
			EmailAlreadyExistsFault_Exception {
		client.ctrlInit(0);
		client.activateUser(USER_EMAIL);
		client.addPoints(USER_EMAIL+1, 10);
	}
}
