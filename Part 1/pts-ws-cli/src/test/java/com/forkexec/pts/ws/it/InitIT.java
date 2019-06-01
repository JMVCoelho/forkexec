package com.forkexec.pts.ws.it;

import static org.junit.Assert.assertEquals;

import com.forkexec.pts.ws.BadInitFault_Exception;
import com.forkexec.pts.ws.EmailAlreadyExistsFault_Exception;
import com.forkexec.pts.ws.InvalidEmailFault_Exception;

import org.junit.After;
import org.junit.Test;

/**
 * Class that tests Init operation
 */
public class InitIT extends BaseIT {

	@After
    public void tearDown() {
        client.ctrlClear();
    }

	@Test
	public void success()
			throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception, BadInitFault_Exception {
		client.activateUser(USER_EMAIL);
		int default_initial_base = client.pointsBalance(USER_EMAIL);
		client.ctrlInit(default_initial_base + 1);
		
		client.activateUser(USER_EMAIL+1);
		assertEquals(default_initial_base + 1, client.pointsBalance(USER_EMAIL+1));
	}
}
