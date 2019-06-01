package com.forkexec.pts.ws.it;

import static org.junit.Assert.assertEquals;

import com.forkexec.pts.ws.BadInitFault_Exception;
import com.forkexec.pts.ws.EmailAlreadyExistsFault_Exception;
import com.forkexec.pts.ws.InvalidEmailFault_Exception;

import org.junit.After;
import org.junit.Test;

/**
 * Class that tests Clear operation
 */
public class ClearIT extends BaseIT {
	@After
    public void tearDown() {
        client.ctrlClear();
    }

	@Test
	public void deletedUser() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser(USER_EMAIL);
		client.ctrlClear();
		client.activateUser(USER_EMAIL);
	}

	@Test
	public void multipleDeletedUsers() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		for (int i = 0; i < 5; i++)
			client.activateUser(USER_EMAIL + i);
		client.ctrlClear();
		for (int i = 0; i < 5; i++)
			client.activateUser(USER_EMAIL + i);
	}

	@Test
	public void noUsers() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.ctrlClear();
		for (int i = 0; i < 5; i++)
			client.activateUser(USER_EMAIL + i);
	}

	@Test
	public void DefaultInitialBaseReset()
			throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception, BadInitFault_Exception {
		client.activateUser(USER_EMAIL);
		int default_initial_base = client.pointsBalance(USER_EMAIL);
		client.ctrlInit(default_initial_base + 1 );

		client.ctrlClear();

		client.activateUser(USER_EMAIL);
		assertEquals(default_initial_base, client.pointsBalance(USER_EMAIL));
	}
}
