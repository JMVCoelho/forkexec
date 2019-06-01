package com.forkexec.rst.ws;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import com.forkexec.rst.domain.Restaurant;

/**
 * This class implements the Web Service port type (interface). The annotations
 * below "map" the Java class to the WSDL definitions.
 */
@WebService(endpointInterface = "com.forkexec.rst.ws.RestaurantPortType",
            wsdlLocation = "RestaurantService.wsdl",
            name ="RestaurantWebService",
            portName = "RestaurantPort",
            targetNamespace="http://ws.rst.forkexec.com/",
            serviceName = "RestaurantService"
)
public class RestaurantPortImpl implements RestaurantPortType {

	/**
	 * The Endpoint manager controls the Web Service instance during its whole
	 * lifecycle.
	 */
	private RestaurantEndpointManager endpointManager;

	/** Constructor receives a reference to the endpoint manager. */
	public RestaurantPortImpl(RestaurantEndpointManager endpointManager) {
		this.endpointManager = endpointManager;
	}
	
	// Main operations -------------------------------------------------------
	
	@Override
	public Menu getMenu(MenuId menuId) throws BadMenuIdFault_Exception {

		try {
			String id = menuId.getId();
		
			Restaurant restaurant = Restaurant.getInstance();
			com.forkexec.rst.domain.Menu menu = restaurant.getMenu(id);

			if (menu != null) {
				Menu _menu = buildWSMenu(menu);
				return _menu;
			}

		}
		catch (com.forkexec.rst.domain.BadMenuIdFault_Exception e) {
			throwBadMenuIdException(e);
		}
		return null;
	}
	
	@Override
	public List<Menu> searchMenus(String descriptionText) throws BadTextFault_Exception {
		try {
			//List of Dmn Menus
			List<com.forkexec.rst.domain.Menu> menus;
			//List of WS Menus
			List<Menu> _menus = new ArrayList<Menu>();
			
			Restaurant restaurant = Restaurant.getInstance();
			menus = restaurant.searchMenus(descriptionText);

			if(menus != null) {
				for(com.forkexec.rst.domain.Menu menu : menus) {
					_menus.add(buildWSMenu(menu));
				}
				return _menus;
			}
		}
		catch (com.forkexec.rst.domain.BadTextFault_Exception e) {
			throwBadTextException(e);
		}
		return null;
	}

	@Override
	public MenuOrder orderMenu(MenuId arg0, int arg1)
			throws BadMenuIdFault_Exception, BadQuantityFault_Exception, InsufficientQuantityFault_Exception {
		
		try {		
			String id = arg0.getId();
			com.forkexec.rst.domain.MenuOrder order;
			MenuOrder _order;

			Restaurant restaurant = Restaurant.getInstance();
			order = restaurant.orderMenu(id, arg1);

			if (order != null) {
				_order = buildWSOrder(order);
				return _order;
			}	
		}
		catch (com.forkexec.rst.domain.BadMenuIdFault_Exception e) {
			throwBadMenuIdException(e);
		}
		catch (com.forkexec.rst.domain.BadQuantityFault_Exception e) {
			throwBadQuantitytException(e);
		}
		catch (com.forkexec.rst.domain.InsufficientQuantityFault_Exception e) {
			throwInsufficientQuantityException(e);
		}
		return null;
	}


	/** Diagnostic operation to check if service is running. */
	@Override
	public String ctrlPing(String inputMessage) {

		// If the park does not have a name, return a default.
		String wsName = endpointManager.getWsName();
		if (wsName == null || wsName.trim().length() == 0)
			wsName = "Restaurant";

		if (inputMessage == null ) {
			return wsName;
		}

		// If no input is received, return a default name.
		if (inputMessage.trim().length() == 0)
			inputMessage = "friend";

		// Build a string with a message to return.
		StringBuilder builder = new StringBuilder();
		builder.append("Hello ").append(inputMessage);
		builder.append(" from ").append(wsName);
		return builder.toString();
	}

	/** Return all variables to default values. */
	@Override
	public void ctrlClear() {
		Restaurant restaurant = Restaurant.getInstance();
		restaurant.reset();
	}

	/** Set variables with specific values. */
	@Override
	public void ctrlInit(List<MenuInit> initialMenus) throws BadInitFault_Exception {
	
		try {
			Restaurant restaurant = Restaurant.getInstance();

			for(MenuInit init : initialMenus) {
				Menu _menu = init.menu;
				int quantity = init.quantity;

				com.forkexec.rst.domain.Menu menu = buildDmnMenu(_menu, quantity);

				restaurant.registerMenu(menu);				
				System.out.println("Registed" + menu.toString());

			}
		}
		catch (com.forkexec.rst.domain.BadInitFault_Exception e) {
			throwBadInitException(e);
		}
		
	}

	// Helper to convert Domain Menu to WS Menu
	private Menu buildWSMenu(com.forkexec.rst.domain.Menu menu) {
		//new WS Menu
		Menu _menu = new Menu();

		//New WS MenuId and set it based on gived Dmn Menu
		MenuId _menuId = new MenuId();
		_menuId.setId(menu.getMenuId());

		//Set WS Menu based on given Dmn Menu
		_menu.setId(_menuId);
		_menu.setEntree(menu.getEntree());
		_menu.setPlate(menu.getPlate());
		_menu.setDessert(menu.getDessert());
		_menu.setPrice(menu.getPrice());
		_menu.setPreparationTime(menu.getPreparationTime());

		return _menu;
	}

	private com.forkexec.rst.domain.Menu buildDmnMenu(Menu menu, int quantity) {

		String _menuId;
		
		_menuId = menu.getId().getId();

		com.forkexec.rst.domain.Menu _menu = new com.forkexec.rst.domain.Menu(_menuId, menu.getEntree(), menu.getPlate(), 
		menu.getDessert(),menu.getPrice(),menu.getPreparationTime(), quantity);

		return _menu;
		
	}


	// Helper to convert Domain MenuOrder to WS Menu
	private MenuOrder buildWSOrder(com.forkexec.rst.domain.MenuOrder order) {
		//new WS Order
		MenuOrder _order = new MenuOrder();

		//New WS MenuOrderId and set it based on gived Dmn Order
		MenuOrderId _menuOrderId = new MenuOrderId();
		_menuOrderId.setId(order.getId());

		//New WS MenuId and set it based on gived Dmn Order
		MenuId _menuId = new MenuId();
		_menuId.setId(order.getMenuId());

		//Set WS Order based on given Dmn Order
		_order.setId(_menuOrderId);
		_order.setMenuQuantity(order.getMenuQuantity());
		_order.setMenuId(_menuId);
		
		return _order;
	}
	
	// Exception helpers -----------------------------------------------------

	private void throwBadMenuIdException(com.forkexec.rst.domain.BadMenuIdFault_Exception e) throws BadMenuIdFault_Exception {
		BadMenuIdFault faultInfo = new BadMenuIdFault();
		faultInfo.message = e.getMessage();
		throw new BadMenuIdFault_Exception(e.getMessage(), faultInfo);
	}

	private void throwBadTextException(com.forkexec.rst.domain.BadTextFault_Exception e) throws BadTextFault_Exception {
		BadTextFault faultInfo = new BadTextFault();
		faultInfo.message = e.getMessage();
		throw new BadTextFault_Exception(e.getMessage(), faultInfo);
	}

	private void throwBadQuantitytException(com.forkexec.rst.domain.BadQuantityFault_Exception e) throws BadQuantityFault_Exception {
		BadQuantityFault faultInfo = new BadQuantityFault();
		faultInfo.message = e.getMessage();
		throw new BadQuantityFault_Exception(e.getMessage(), faultInfo);
	}

	private void throwInsufficientQuantityException(com.forkexec.rst.domain.InsufficientQuantityFault_Exception e) throws InsufficientQuantityFault_Exception {
		InsufficientQuantityFault faultInfo = new InsufficientQuantityFault();
		faultInfo.message = e.getMessage();
		throw new InsufficientQuantityFault_Exception(e.getMessage(), faultInfo);
	}

	private void throwBadInitException(com.forkexec.rst.domain.BadInitFault_Exception e) throws BadInitFault_Exception{
		BadInitFault faultInfo = new BadInitFault();
		faultInfo.message = e.getMessage();
		throw new BadInitFault_Exception(e.getMessage(), faultInfo);
	}

}
