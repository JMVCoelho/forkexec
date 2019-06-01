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
public class AccountBalanceIT extends BaseIT {
    private FoodId fid1;
    private FoodId fid3;

    @Before
    public void initializeRestaurants() throws InvalidInitFault_Exception, InvalidFoodIdFault_Exception,
            InvalidFoodQuantityFault_Exception, InvalidUserIdFault_Exception {
        // Will create 1 menus for each one of restaurants
        List<FoodInit> foodInitList = new ArrayList<FoodInit>();

        Food food1 = new Food();
        fid1 = new FoodId();

        fid1.setMenuId("1");
        fid1.setRestaurantId("A40_Restaurant1");

        food1.setId(fid1);
        food1.setDessert("gelado");
        food1.setEntree("pao");
        food1.setPlate("carne");
        food1.setPreparationTime(100);
        food1.setPrice(20);

        Food food3 = new Food();
        fid3 = new FoodId();

        fid3.setMenuId("3");
        fid3.setRestaurantId("A40_Restaurant2");

        food3.setId(fid3);
        food3.setDessert("gelado");
        food3.setEntree("chouriço");
        food3.setPlate("macrobiotica");
        food3.setPreparationTime(25);
        food3.setPrice(20);



        FoodInit finit1 = new FoodInit();
        FoodInit finit3 = new FoodInit();

        finit1.setQuantity(3);
        finit3.setQuantity(3);
        finit1.setFood(food1);
        finit3.setFood(food3);

        foodInitList.add(finit1);
        foodInitList.add(finit3);

        client.ctrlInitFood(foodInitList);
        client.activateAccount("test@user");
        
    }

    @After
    public void tearDown() {
        client.ctrlClear();
    }

    @Test
    public void success() throws InvalidUserIdFault_Exception, InvalidFoodIdFault_Exception, InvalidFoodQuantityFault_Exception, EmptyCartFault_Exception, NotEnoughPointsFault_Exception {
        int initialAmmount = client.accountBalance("test@user");
        client.addFoodToCart("test@user", fid1, 1);
        client.addFoodToCart("test@user", fid3, 1);
        client.orderCart("test@user");
        assertEquals(initialAmmount-20-20,client.accountBalance("test@user"));
    }

    @Test(expected = InvalidUserIdFault_Exception.class)
    public void unregisteredUser() throws InvalidUserIdFault_Exception {
        client.accountBalance("test@user1");
    }
}