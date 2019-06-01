package com.forkexec.rst.ws.it;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import com.forkexec.rst.ws.BadInitFault_Exception;
import com.forkexec.rst.ws.BadMenuIdFault_Exception;
import com.forkexec.rst.ws.BadQuantityFault_Exception;
import com.forkexec.rst.ws.InsufficientQuantityFault_Exception;
import com.forkexec.rst.ws.Menu;
import com.forkexec.rst.ws.MenuId;
import com.forkexec.rst.ws.MenuInit;
import com.forkexec.rst.ws.MenuOrder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Class that tests getMenu operation
 */
public class OrderMenuIT extends BaseIT {
    private MenuId id = new MenuId();
    private MenuId id2 = new MenuId();



    @Before
    public void addMenus() throws BadInitFault_Exception {
        // Creating Two Menus ----------------------------
        List<MenuInit> menus = new ArrayList<MenuInit>();
        id.setId("1");
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
    public void validOrder() throws BadMenuIdFault_Exception, BadQuantityFault_Exception, InsufficientQuantityFault_Exception{
        
        MenuOrder returned = client.orderMenu(id, 1);

        assertNotNull(returned);
        assertEquals(returned.getMenuId().getId(), "1");
        assertEquals(returned.getMenuQuantity(), 1);
        assertEquals(returned.getId().getId(), "1");

    }

    @Test
    public void validOrders() throws BadMenuIdFault_Exception, BadQuantityFault_Exception, InsufficientQuantityFault_Exception{
        //This test verifies whether orderId is being correctly generated
        MenuOrder returned = client.orderMenu(id, 1);
        MenuOrder returned2 = client.orderMenu(id2, 1);

        assertNotNull(returned);
        assertEquals(returned.getMenuId().getId(), "1");
        assertEquals(returned.getMenuQuantity(), 1);
        assertEquals(returned.getId().getId(), "1");

        assertNotNull(returned2);
        assertEquals(returned2.getMenuId().getId(), "2");
        assertEquals(returned2.getMenuQuantity(), 1);
        assertEquals(returned2.getId().getId(), "2");
    }

    @Test(expected = InsufficientQuantityFault_Exception.class)
    public void invalidOrder_Insufficient() throws BadMenuIdFault_Exception, BadQuantityFault_Exception, InsufficientQuantityFault_Exception{
        client.orderMenu(id, 11);
    }

    @Test(expected = BadQuantityFault_Exception.class)
    public void invalidOrder_Negative() throws BadMenuIdFault_Exception, BadQuantityFault_Exception, InsufficientQuantityFault_Exception{
    
        client.orderMenu(id, -1);
    }

    @Test(expected = InsufficientQuantityFault_Exception.class)
    public void invalidOrder_Insufficient2() throws BadMenuIdFault_Exception, BadQuantityFault_Exception, InsufficientQuantityFault_Exception{
        MenuOrder returned = client.orderMenu(id2, 15); //This is valid

        assertNotNull(returned);
        assertEquals(returned.getMenuId().getId(), "2");
        assertEquals(returned.getMenuQuantity(), 15);
        assertEquals(returned.getId().getId(), "1"); //Will be one cuz its the only order

        client.orderMenu(id2, 1); //This one will throw the exception

    }

    @Test(expected = BadMenuIdFault_Exception.class)
    public void invalidOrder_EmptyMenuId() throws BadMenuIdFault_Exception, BadQuantityFault_Exception, InsufficientQuantityFault_Exception{
        MenuId id = new MenuId();
        id.setId("");
        client.orderMenu(id, 1);
    }

    @Test(expected = BadMenuIdFault_Exception.class)
    public void invalidOrder_WhiteSpaceMenuId() throws BadMenuIdFault_Exception, BadQuantityFault_Exception, InsufficientQuantityFault_Exception{
        MenuId id = new MenuId();
        id.setId("   ");
        client.orderMenu(id, 1);
    }

    
}