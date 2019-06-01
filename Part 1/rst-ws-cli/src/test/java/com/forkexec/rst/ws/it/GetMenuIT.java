package com.forkexec.rst.ws.it;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;


import java.util.ArrayList;
import java.util.List;

import com.forkexec.rst.ws.BadInitFault_Exception;
import com.forkexec.rst.ws.BadMenuIdFault_Exception;
import com.forkexec.rst.ws.Menu;
import com.forkexec.rst.ws.MenuId;
import com.forkexec.rst.ws.MenuInit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Class that tests getMenu operation
 */
public class GetMenuIT extends BaseIT {
    
    @Before
    public void addOneMenu() {

    }

    @After
    public void tearDown() {
        client.ctrlClear();
    }

    @Test
    public void getOneCorrectMenu() throws BadInitFault_Exception, BadMenuIdFault_Exception{
        
        List<MenuInit> menus = new ArrayList<MenuInit>();
        MenuId id = new MenuId();
        id.setId("1");
        
        Menu menu = new Menu();
        menu.setId(id);
        menu.setEntree("entrada");
        menu.setPlate("prato");
        menu.setDessert("sobremesa");
        menu.setPrice(1);
        menu.setPreparationTime(10);

        MenuInit menu1 = new MenuInit();
        menu1.setMenu(menu);
        menu1.setQuantity(10);

        menus.add(menu1);

    
        client.ctrlInit(menus);
        Menu returned = client.getMenu(id);

        assertNotNull(returned);
        assertEquals(returned.getId().getId(), "1");
        assertEquals(returned.getEntree(), "entrada");
        assertEquals(returned.getPlate(), "prato");
        assertEquals(returned.getDessert(), "sobremesa");
        assertEquals(returned.getPrice(), 1);
        assertEquals(returned.getPreparationTime(), 10);

    }
    
    @Test
    public void getTwoCorrectMenu() throws BadInitFault_Exception, BadMenuIdFault_Exception {
        // Creating Two Menus ----------------------------
        List<MenuInit> menus = new ArrayList<MenuInit>();
        MenuId id = new MenuId();
        id.setId("1");
        MenuId id2 = new MenuId();
        id2.setId("2");
        
        Menu menuDesc1 = new Menu();
        menuDesc1.setId(id);
        menuDesc1.setEntree("entrada");
        menuDesc1.setPlate("prato");
        menuDesc1.setDessert("sobremesa");
        menuDesc1.setPrice(1);
        menuDesc1.setPreparationTime(10);

        Menu menuDesc2 = new Menu();
        menuDesc2.setId(id2);
        menuDesc2.setEntree("pao");
        menuDesc2.setPlate("carne");
        menuDesc2.setDessert("fruta");
        menuDesc2.setPrice(3);
        menuDesc2.setPreparationTime(15);

        MenuInit menu1 = new MenuInit();
        menu1.setMenu(menuDesc1);
        menu1.setQuantity(10);

        MenuInit menu2 = new MenuInit();
        menu2.setMenu(menuDesc2);
        menu2.setQuantity(15);

        //------------------------------------------
        //Adding Both to the initial menu init array

        menus.add(menu1);
        menus.add(menu2);
        
        //------------------------------
        //Initializing and getting menus

        client.ctrlInit(menus);
        Menu returned1 = client.getMenu(id);
        Menu returned2 = client.getMenu(id2);

        //-------------------------------------
        //Both are successfuly set and returned

        assertNotNull(returned1);
        assertEquals(returned1.getId().getId(), "1");
        assertEquals(returned1.getEntree(), "entrada");
        assertEquals(returned1.getPlate(), "prato");
        assertEquals(returned1.getDessert(), "sobremesa");
        assertEquals(returned1.getPrice(), 1);
        assertEquals(returned1.getPreparationTime(), 10);

        assertNotNull(returned2);
        assertEquals(returned2.getId().getId(), "2");
        assertEquals(returned2.getEntree(), "pao");
        assertEquals(returned2.getPlate(), "carne");
        assertEquals(returned2.getDessert(), "fruta");
        assertEquals(returned2.getPrice(), 3);
        assertEquals(returned2.getPreparationTime(), 15);
    }

    @Test
    public void getUnexistentMenu() throws BadMenuIdFault_Exception{
        //When trying to get a menu passing an invalid id(only whitespaces), exception is thrown
        MenuId id = new MenuId();
        id.setId("5");
        Menu returned = client.getMenu(id);

        assertNull(returned);

    }

    @Test(expected = BadMenuIdFault_Exception.class)
    public void BadMenuId_EMPTY() throws BadMenuIdFault_Exception{
        //When trying to get a menu passing an invalid id(empty), exception is thrown
        MenuId id = new MenuId();
        id.setId("");
        client.getMenu(id);
    
    }

    @Test(expected = BadMenuIdFault_Exception.class)
    public void BadMenuId_WHITESPACE() throws BadMenuIdFault_Exception{
        //When trying to get a menu passing an invalid id(only whitespaces), exception is thrown
        MenuId id = new MenuId();
        id.setId("   ");
        client.getMenu(id);

    }

    
}