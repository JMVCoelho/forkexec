package com.forkexec.cc.ws.it;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


import org.junit.Test;

/**
 * Class that tests verifyCard operation
 */
public class VerifyIT extends BaseIT {

	@Test
	public void ValidCard() {
		assertTrue(client.validateNumber("4024007102923926"));
    }
    
    @Test
	public void InvalidCard_SHORT() {
		assertFalse(client.validateNumber("402400710292392"));
    }
    
    @Test
	public void InvalidCard_BIG() {
		assertFalse(client.validateNumber("40240071029239266"));
	}

    @Test
	public void InvalidCard_INVALID() {
		assertFalse(client.validateNumber("4024007102923922"));
	}
}