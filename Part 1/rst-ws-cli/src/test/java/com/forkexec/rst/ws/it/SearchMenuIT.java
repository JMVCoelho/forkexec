package com.forkexec.rst.ws.it;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;


import java.util.ArrayList;
import java.util.List;

import com.forkexec.rst.ws.BadInitFault_Exception;
import com.forkexec.rst.ws.BadTextFault_Exception;
import com.forkexec.rst.ws.Menu;
import com.forkexec.rst.ws.MenuId;
import com.forkexec.rst.ws.MenuInit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Class that tests getMenu operation
 */
public class SearchMenuIT extends BaseIT {

    @Before
    public void addMenus() throws BadInitFault_Exception {
        // Creating Two Menus ----------------------------
        List<MenuInit> menus = new ArrayList<MenuInit>();
        MenuId id = new MenuId();
        id.setId("1");
        MenuId id2 = new MenuId();
        id2.setId("2");
        
        Menu menuDesc1 = new Menu();
        menuDesc1.setId(id);
        menuDesc1.setEntree("Queijo gratinado");
        menuDesc1.setPlate("Secretos de Porco");
        menuDesc1.setDessert("Bolo de Chocolate");
        menuDesc1.setPrice(1);
        menuDesc1.setPreparationTime(10);

        Menu menuDesc2 = new Menu();
        menuDesc2.setId(id2);
        menuDesc2.setEntree("Ovos Mexidos com Espargos");
        menuDesc2.setPlate("Dourada Grelhada");
        menuDesc2.setDessert("Bolo de Laranja");
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

        client.ctrlInit(menus);
    }

    @After
    public void tearDown() {
        client.ctrlClear();
    }

    @Test
    public void searchAvaiableInOneMenu() throws BadTextFault_Exception{
        
        List<Menu> returned = client.searchMenus("Porco");

        assertNotNull(returned);
        assertEquals(returned.size(), 1);
        assertEquals(returned.get(0).getPlate(), "Secretos de Porco");
    }

    @Test
    public void isCaseSensitive() throws BadTextFault_Exception{
        
        List<Menu> returned = client.searchMenus("porco");

        assertNotNull(returned);
        assertEquals(returned.size(), 0);
    }

    @Test
    public void searchAvailableInTwoMenus() throws BadTextFault_Exception{
        
        List<Menu> returned = client.searchMenus("Bolo");

        assertNotNull(returned);
        assertEquals(returned.size(), 2);
        assertEquals(returned.get(0).getDessert(), "Bolo de Chocolate");
        assertEquals(returned.get(1).getDessert(), "Bolo de Laranja");
    }

    @Test(expected = BadTextFault_Exception.class)
    public void emptySearch() throws BadTextFault_Exception{
        
        client.searchMenus("");

    }
    @Test(expected = BadTextFault_Exception.class)
    public void searchWithEmptySpaces() throws BadTextFault_Exception{
        
        client.searchMenus("Bolo de Laranja");

    }


    
}