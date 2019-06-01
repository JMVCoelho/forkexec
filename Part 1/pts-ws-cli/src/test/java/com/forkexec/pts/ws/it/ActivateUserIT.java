package com.forkexec.pts.ws.it;

import org.junit.After;
import org.junit.Test;

import com.forkexec.pts.ws.EmailAlreadyExistsFault_Exception;
import com.forkexec.pts.ws.InvalidEmailFault_Exception;
/**
 * Class that tests Init operation
 */
public class ActivateUserIT extends BaseIT {
	private String USER_EMAIL = "test@user";
	@After
    public void tearDown() {
        client.ctrlClear();
    }

	@Test
	public void success() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser(USER_EMAIL);
	}

	@Test(expected = EmailAlreadyExistsFault_Exception.class)
	public void sameUser() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser(USER_EMAIL);
		client.activateUser(USER_EMAIL);
	}
	
	@Test(expected = InvalidEmailFault_Exception.class)
	public void invalidEmail1() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser("");
	}

	@Test(expected = InvalidEmailFault_Exception.class)
	public void invalidEmail2() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser("f");
	}

	@Test(expected = InvalidEmailFault_Exception.class)
	public void invalidEmail3() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser("f@f@f");
	}

	@Test(expected = InvalidEmailFault_Exception.class)
	public void invalidEmail4() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser("@");
	}

	@Test(expected = InvalidEmailFault_Exception.class)
	public void invalidEmail5() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser("f@");
	}

	@Test(expected = InvalidEmailFault_Exception.class)
	public void invalidEmail6() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser("@f");
	}

	@Test(expected = InvalidEmailFault_Exception.class)
	public void invalidEmail7() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser("f.@f");
	}

	@Test(expected = InvalidEmailFault_Exception.class)
	public void invalidEmail8() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser(".f@f");
	}

	@Test(expected = InvalidEmailFault_Exception.class)
	public void invalidEmail9() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser(".f@f");
	}

	@Test(expected = InvalidEmailFault_Exception.class)
	public void invalidEmail10() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser("f..f@f");
	}

	@Test(expected = InvalidEmailFault_Exception.class)
	public void invalidEmail11() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser("f@f.");
	}

	@Test(expected = InvalidEmailFault_Exception.class)
	public void invalidEmail12() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser("f@.f");
	}

	@Test(expected = InvalidEmailFault_Exception.class)
	public void invalidEmail13() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser("f@f..f");
	}

	@Test
	public void validEmail1() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser("v@v");
	}
	
	@Test
	public void validEmail2() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser("1234567665761423157673456300_victory_ãÎííúýrçççàìùỳ_vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv@vvvv");
	}

	@Test
	public void invalidEmai3() throws EmailAlreadyExistsFault_Exception, InvalidEmailFault_Exception {
		client.activateUser("v.vvv.v@v.vvv.vv.v");
	}
}
