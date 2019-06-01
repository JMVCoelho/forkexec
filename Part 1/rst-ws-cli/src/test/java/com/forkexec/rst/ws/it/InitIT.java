package com.forkexec.rst.ws.it;

import java.util.ArrayList;
import java.util.List;

import com.forkexec.rst.ws.BadInitFault_Exception;
import com.forkexec.rst.ws.Menu;
import com.forkexec.rst.ws.MenuId;
import com.forkexec.rst.ws.MenuInit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Class that tests getMenu operation
 */
public class InitIT extends BaseIT {
    
    @Before
    public void addOneMenu() {

    }

    @After
    public void tearDown() {
        client.ctrlClear();
    }

    @Test(expected = BadInitFault_Exception.class)
    public void DuplicateMenuId() throws BadInitFault_Exception {
        
        List<MenuInit> menus = new ArrayList<MenuInit>();
        MenuId id = new MenuId();
        id.setId("1");
        MenuId id2 = new MenuId();
        id2.setId("1");
        
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

        menus.add(menu1);
        menus.add(menu2);

        client.ctrlInit(menus);
    }

    @Test(expected = BadInitFault_Exception.class)
    public void InvalidMenuId_EMPTY() throws BadInitFault_Exception {
        
        List<MenuInit> menus = new ArrayList<MenuInit>();
        MenuId id = new MenuId();
        id.setId("");
        
        Menu menuDesc1 = new Menu();
        menuDesc1.setId(id);
        menuDesc1.setEntree("entrada");
        menuDesc1.setPlate("prato");
        menuDesc1.setDessert("sobremesa");
        menuDesc1.setPrice(1);
        menuDesc1.setPreparationTime(10);

        MenuInit menu1 = new MenuInit();
        menu1.setMenu(menuDesc1);
        menu1.setQuantity(10);

        menus.add(menu1);

        client.ctrlInit(menus);
    }

    
    @Test(expected = BadInitFault_Exception.class)
    public void InvalidDessert() throws BadInitFault_Exception {
        
        List<MenuInit> menus = new ArrayList<MenuInit>();
        MenuId id = new MenuId();
        id.setId("1");
        
        Menu menuDesc1 = new Menu();
        menuDesc1.setId(id);
        menuDesc1.setEntree("entrada");
        menuDesc1.setPlate("prato");
        menuDesc1.setDessert("");
        menuDesc1.setPrice(1);
        menuDesc1.setPreparationTime(10);

        MenuInit menu1 = new MenuInit();
        menu1.setMenu(menuDesc1);
        menu1.setQuantity(10);

        menus.add(menu1);

        client.ctrlInit(menus);
    }

    @Test(expected = BadInitFault_Exception.class)
    public void InvalidPlate() throws BadInitFault_Exception {
        
        List<MenuInit> menus = new ArrayList<MenuInit>();
        MenuId id = new MenuId();
        id.setId("1");
        
        Menu menuDesc1 = new Menu();
        menuDesc1.setId(id);
        menuDesc1.setEntree("entrada");
        menuDesc1.setPlate("");
        menuDesc1.setDessert("sobremesa");
        menuDesc1.setPrice(1);
        menuDesc1.setPreparationTime(10);

        MenuInit menu1 = new MenuInit();
        menu1.setMenu(menuDesc1);
        menu1.setQuantity(10);

        menus.add(menu1);

        client.ctrlInit(menus);
    }

    @Test(expected = BadInitFault_Exception.class)
    public void InvalidEntree() throws BadInitFault_Exception {
        
        List<MenuInit> menus = new ArrayList<MenuInit>();
        MenuId id = new MenuId();
        id.setId("1");
        
        Menu menuDesc1 = new Menu();
        menuDesc1.setId(id);
        menuDesc1.setEntree("");
        menuDesc1.setPlate("prato");
        menuDesc1.setDessert("sobremesa");
        menuDesc1.setPrice(1);
        menuDesc1.setPreparationTime(10);

        MenuInit menu1 = new MenuInit();
        menu1.setMenu(menuDesc1);
        menu1.setQuantity(10);

        menus.add(menu1);

        client.ctrlInit(menus);
    }

    @Test(expected = BadInitFault_Exception.class)
    public void InvalidPrice() throws BadInitFault_Exception {
        
        List<MenuInit> menus = new ArrayList<MenuInit>();
        MenuId id = new MenuId();
        id.setId("1");
        
        Menu menuDesc1 = new Menu();
        menuDesc1.setId(id);
        menuDesc1.setEntree("entrada");
        menuDesc1.setPlate("prato");
        menuDesc1.setDessert("sobremesa");
        menuDesc1.setPrice(-1);
        menuDesc1.setPreparationTime(10);

        MenuInit menu1 = new MenuInit();
        menu1.setMenu(menuDesc1);
        menu1.setQuantity(10);

        menus.add(menu1);

        client.ctrlInit(menus);
    }

    @Test(expected = BadInitFault_Exception.class)
    public void InvalidPrepTime() throws BadInitFault_Exception {
        
        List<MenuInit> menus = new ArrayList<MenuInit>();
        MenuId id = new MenuId();
        id.setId("1");
        
        Menu menuDesc1 = new Menu();
        menuDesc1.setId(id);
        menuDesc1.setEntree("entrada");
        menuDesc1.setPlate("prato");
        menuDesc1.setDessert("sobremesa");
        menuDesc1.setPrice(1);
        menuDesc1.setPreparationTime(-10);

        MenuInit menu1 = new MenuInit();
        menu1.setMenu(menuDesc1);
        menu1.setQuantity(10);

        menus.add(menu1);

        client.ctrlInit(menus);
    }

    @Test(expected = BadInitFault_Exception.class)
    public void InvalidQuantity() throws BadInitFault_Exception {
        
        List<MenuInit> menus = new ArrayList<MenuInit>();
        MenuId id = new MenuId();
        id.setId("1");
        
        Menu menuDesc1 = new Menu();
        menuDesc1.setId(id);
        menuDesc1.setEntree("entrada");
        menuDesc1.setPlate("prato");
        menuDesc1.setDessert("sobremesa");
        menuDesc1.setPrice(1);
        menuDesc1.setPreparationTime(10);

        MenuInit menu1 = new MenuInit();
        menu1.setMenu(menuDesc1);
        menu1.setQuantity(-10);

        menus.add(menu1);

        client.ctrlInit(menus);
    }
    
}