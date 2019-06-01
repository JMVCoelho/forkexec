package com.forkexec.hub.ws.it;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import com.forkexec.hub.ws.Food;
import com.forkexec.hub.ws.FoodId;
import com.forkexec.hub.ws.FoodInit;
import com.forkexec.hub.ws.InvalidInitFault_Exception;
import com.forkexec.hub.ws.InvalidTextFault_Exception;

import org.junit.After;
import org.junit.Before;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Class that tests search Deal operation
 */
public class SearchDealIT extends BaseIT {

    @Before
    public void initializeRestaurants() throws InvalidInitFault_Exception {
        // Will create 2 menus for each one of restaurants
        List<FoodInit> foodInitList = new ArrayList<FoodInit>();

        Food food1 = new Food();
        FoodId fid1 = new FoodId();

        fid1.setMenuId("1");
        fid1.setRestaurantId("A40_Restaurant1");

        food1.setId(fid1);
        food1.setDessert("gelado");
        food1.setEntree("pao");
        food1.setPlate("carne");
        food1.setPreparationTime(100);
        food1.setPrice(50);

        Food food2 = new Food();
        FoodId fid2 = new FoodId();

        fid2.setMenuId("2");
        fid2.setRestaurantId("A40_Restaurant1");

        food2.setId(fid2);
        food2.setDessert("gelado");
        food2.setEntree("ovos");
        food2.setPlate("peixe");
        food2.setPreparationTime(50);
        food2.setPrice(100);

        Food food3 = new Food();
        FoodId fid3 = new FoodId();

        fid3.setMenuId("3");
        fid3.setRestaurantId("A40_Restaurant2");

        food3.setId(fid3);
        food3.setDessert("gelado");
        food3.setEntree("chouriço");
        food3.setPlate("macrobiotica");
        food3.setPreparationTime(25);
        food3.setPrice(150);

        Food food4 = new Food();
        FoodId fid4 = new FoodId();

        fid4.setMenuId("4");
        fid4.setRestaurantId("A40_Restaurant2");

        food4.setId(fid4);
        food4.setDessert("gelado");
        food4.setEntree("cogumelos");
        food4.setPlate("carne");
        food4.setPreparationTime(12);
        food4.setPrice(200);

        FoodInit finit1 = new FoodInit();
        FoodInit finit2 = new FoodInit();
        FoodInit finit3 = new FoodInit();
        FoodInit finit4 = new FoodInit();

        finit1.setQuantity(5);
        finit2.setQuantity(5);
        finit3.setQuantity(5);
        finit4.setQuantity(5);
        finit1.setFood(food1);
        finit2.setFood(food2);
        finit3.setFood(food3);
        finit4.setFood(food4);

        foodInitList.add(finit1);
        foodInitList.add(finit2);
        foodInitList.add(finit3);
        foodInitList.add(finit4);

        client.ctrlInitFood(foodInitList);

    }

    @After
    public void tearDown() {
        client.ctrlClear();
    }

    @Test
    public void correctSearch_ALLMENUS() throws InvalidTextFault_Exception {
        List<Food> foods = client.searchDeal("gelado");

        assertNotNull(foods);
        assertEquals(foods.size(), 4);
        assertEquals(foods.get(0).getPrice(), 50);
    }
    
    @Test
    public void correctSearch_NOTALLMENUS() throws InvalidTextFault_Exception {
        List<Food> foods = client.searchDeal("carne");

        assertNotNull(foods);
        assertEquals(foods.size(), 2);
        assertEquals(foods.get(0).getPrice(), 50);
    }
    @Test
    public void correctSearch_SINGLEMENU() throws InvalidTextFault_Exception {
        List<Food> foods = client.searchHungry("macrobiotica");

        assertNotNull(foods);
        assertEquals(foods.size(), 1);
        assertEquals(foods.get(0).getPrice(), 150);
    }
    
    @Test
    public void correctSearch_NOMENU() throws InvalidTextFault_Exception {
        List<Food> foods = client.searchHungry("pato");

        assertNotNull(foods);
        assertEquals(foods.size(), 0);
    }
    
    @Test(expected = InvalidTextFault_Exception.class)
    public void incorrectSearch_EMPTY() throws InvalidTextFault_Exception {
        client.searchHungry("");
    }
    
    @Test(expected = InvalidTextFault_Exception.class)
    public void incorrectSearch_WHITESPACE() throws InvalidTextFault_Exception {
        client.searchHungry("Arroz de pato");
	}


}
