package com.forkexec.hub.ws.it;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import com.forkexec.hub.ws.EmptyCartFault_Exception;
import com.forkexec.hub.ws.Food;
import com.forkexec.hub.ws.FoodId;
import com.forkexec.hub.ws.FoodInit;
import com.forkexec.hub.ws.FoodOrder;
import com.forkexec.hub.ws.InvalidFoodIdFault_Exception;
import com.forkexec.hub.ws.InvalidFoodQuantityFault_Exception;
import com.forkexec.hub.ws.InvalidInitFault_Exception;
import com.forkexec.hub.ws.InvalidUserIdFault_Exception;
import com.forkexec.hub.ws.NotEnoughPointsFault_Exception;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Class that tests search Deal operation
 */
public class ActivateAccountIT extends BaseIT {

    @After
    public void tearDown() {
        client.ctrlClear();
    }

    @Test
    public void success() throws InvalidUserIdFault_Exception{
        client.activateAccount("test@user");
    }

    @Test(expected = InvalidUserIdFault_Exception.class)
    public void invalidUserId() throws InvalidUserIdFault_Exception{
        client.activateAccount("te   ...  tyty  st@@@@ .@.@.user");
    }
}