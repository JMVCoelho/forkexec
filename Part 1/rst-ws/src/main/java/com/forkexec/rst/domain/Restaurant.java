package com.forkexec.rst.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Restaurant
 *
 * A restaurant server.
 *
 */
public class Restaurant {

	private Map<String, Menu> menus = new ConcurrentHashMap<>();
	private Map<String, MenuOrder> orders = new ConcurrentHashMap<>();
	private AtomicInteger orderIdCounter = new AtomicInteger(0);

	// Singleton -------------------------------------------------------------
	
	/** Private constructor prevents instantiation from other classes. */
	private Restaurant() {
		// Initialization of default values
	}

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance()
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class SingletonHolder {
		private static final Restaurant INSTANCE = new Restaurant();
	}

	public static synchronized Restaurant getInstance() {
		return SingletonHolder.INSTANCE;
	}

	
	// Main methods used at RestaurantPortImpl ---------------------------

	public Menu getMenu(String menuId) throws BadMenuIdFault_Exception {
		if(menuId.length() == 0) {
            throw new BadMenuIdFault_Exception("Menu Id cant be empty");
		}
		
		if(menuId.trim().length() == 0) {
            throw new BadMenuIdFault_Exception("Menu Id cant be only whitespaces");
        }
		return menus.get(menuId); //get already returns null if menuId isnt there
	}

	public List<Menu> searchMenus(String description) throws BadTextFault_Exception{
		if (description.length() == 0) {
			throw new BadTextFault_Exception("Description cant be empty");
		}

		if (description.contains(" ")) {
			throw new BadTextFault_Exception("Description cant contain white spaces");
		}

		List<Menu> _menus = new ArrayList<Menu>();
		for(Map.Entry<String, Menu> entry : menus.entrySet()) {
			String entree = entry.getValue().getEntree();
			String plate = entry.getValue().getPlate();
			String dessert = entry.getValue().getDessert();

			if(entree.contains(description) || plate.contains(description) || dessert.contains(description)) {
				_menus.add(entry.getValue());
			}
		}
		return _menus;
	}

	public MenuOrder orderMenu(String menuId, int quantity) 
	throws BadQuantityFault_Exception, InsufficientQuantityFault_Exception, BadMenuIdFault_Exception {

		if(quantity <= 0) {
			throw new BadQuantityFault_Exception("Quantity needs to be an integer bigger than 0");
		}

		Menu menu = getMenu(menuId);
		decreaseMenuQuantity(menu, quantity);

		String orderId = generateOrderId();
		MenuOrder order = new MenuOrder(menuId, quantity, orderId);

		orders.put(orderId, order);

		return order;
	}


	// Auxiliary Methods ---------------------------------------------

	public void decreaseMenuQuantity(Menu menu, int quantity) throws InsufficientQuantityFault_Exception {
		synchronized(menu) {
			int availableQuantity = menu.getQuantity();
			if (availableQuantity < quantity) {
				throw new InsufficientQuantityFault_Exception("Current stock cant handle such big menu order");
			}
			menu.setQuantity(availableQuantity - quantity);
		}
	}

	public void registerMenu(Menu menu) throws BadInitFault_Exception{
		if(menus.containsKey(menu.getMenuId())) {
			throw new BadInitFault_Exception("Duplicated Menu ID");
		}
		if(menu.getMenuId().length() == 0) {
            throw new BadInitFault_Exception("Menu Id cant be empty");
        }		
		if(menu.getEntree().length() == 0) {
			throw new BadInitFault_Exception("Entree cant be empty");
		}
		if(menu.getPlate().length() == 0) {
			throw new BadInitFault_Exception("Plate cant be empty");
		}
		if(menu.getDessert().length() == 0) {
			throw new BadInitFault_Exception("Dessert cant be empty");
		}
		if(menu.getPrice() <= 0) {
			throw new BadInitFault_Exception("Price cant be 0 or lower");
		}
		if(menu.getPreparationTime() <= 0) {
			throw new BadInitFault_Exception("Preparation Time cant be 0 or lower");
		}
		if(menu.getQuantity() <= 0) {
			throw new BadInitFault_Exception("Quantity cant be 0 or lower (error @ init)");
		}

		menus.put(menu.getMenuId(), menu);
	}
	
	//Id generators --------------------------------------------------

	public String generateOrderId() {
		int orderId = orderIdCounter.incrementAndGet();
		return Integer.toString(orderId);
	}

	// Full Reset -----------------------------------------------------
	public void reset() {
		menus.clear();
		orders.clear();
		orderIdCounter.set(0);
	}
}
