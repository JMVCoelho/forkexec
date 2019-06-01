package com.forkexec.hub.ws.it;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import com.forkexec.hub.ws.EmptyCartFault_Exception;
import com.forkexec.hub.ws.Food;
import com.forkexec.hub.ws.FoodId;
import com.forkexec.hub.ws.FoodInit;
import com.forkexec.hub.ws.FoodOrder;
import com.forkexec.hub.ws.InvalidCreditCardFault_Exception;
import com.forkexec.hub.ws.InvalidFoodIdFault_Exception;
import com.forkexec.hub.ws.InvalidFoodQuantityFault_Exception;
import com.forkexec.hub.ws.InvalidInitFault_Exception;
import com.forkexec.hub.ws.InvalidMoneyFault_Exception;
import com.forkexec.hub.ws.InvalidUserIdFault_Exception;
import com.forkexec.hub.ws.NotEnoughPointsFault_Exception;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Class that tests search Deal operation
 */
public class LoadAccountIT extends BaseIT {

    @After
    public void tearDown() {
        client.ctrlClear();
    }

    @Test
    public void success() throws InvalidCreditCardFault_Exception, InvalidMoneyFault_Exception, InvalidUserIdFault_Exception {
    
    client.activateAccount("test@user");
    client.loadAccount("test@user", 10 ,"4024007102923926");
    }

    //@Test(expected = InvalidCreditCardFault_Exception.class)
    //CC checking not implemented
    public void invalidCC() throws InvalidCreditCardFault_Exception, InvalidMoneyFault_Exception, InvalidUserIdFault_Exception {
    
    client.activateAccount("test@user");
    client.loadAccount("test@user", 10 ,"40240071029239266");
    }

    @Test(expected = InvalidMoneyFault_Exception.class)
    public void invalidMoney() throws InvalidCreditCardFault_Exception, InvalidMoneyFault_Exception, InvalidUserIdFault_Exception {
    
    client.activateAccount("test@user");
    client.loadAccount("test@user", 13 ,"4024007102923926");
    }

    @Test(expected = InvalidUserIdFault_Exception.class)
    public void InvalidUser() throws InvalidCreditCardFault_Exception, InvalidMoneyFault_Exception, InvalidUserIdFault_Exception {
    
    client.activateAccount("test@user");
    client.loadAccount("InvalidUserId", 10 ,"4024007102923926");
    }

}