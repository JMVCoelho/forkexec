package com.forkexec.hub.ws;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.jws.WebService;

import com.forkexec.hub.domain.Hub;

import com.forkexec.pts.ws.cli.PointsClient;
import com.forkexec.pts.ws.cli.PointsClientException;
import com.forkexec.pts.ws.EmailAlreadyExistsFault_Exception;
import com.forkexec.pts.ws.InvalidEmailFault_Exception;
import com.forkexec.pts.ws.InvalidPointsFault_Exception;
import com.forkexec.pts.ws.NotEnoughBalanceFault_Exception;
import com.forkexec.rst.ws.BadInitFault_Exception;
import com.forkexec.rst.ws.BadMenuIdFault_Exception;
import com.forkexec.rst.ws.BadQuantityFault_Exception;
import com.forkexec.rst.ws.BadTextFault_Exception;
import com.forkexec.rst.ws.InsufficientQuantityFault_Exception;
import com.forkexec.rst.ws.Menu;
import com.forkexec.rst.ws.MenuId;
import com.forkexec.rst.ws.MenuInit;
import com.forkexec.rst.ws.cli.RestaurantClient;
import com.forkexec.rst.ws.cli.RestaurantClientException;

import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINaming;
import pt.ulisboa.tecnico.sdis.ws.uddi.UDDINamingException;

/**
 * This class implements the Web Service port type (interface). The annotations
 * below "map" the Java class to the WSDL definitions.
 */
@WebService(endpointInterface = "com.forkexec.hub.ws.HubPortType", wsdlLocation = "HubService.wsdl", name = "HubWebService", portName = "HubPort", targetNamespace = "http://ws.hub.forkexec.com/", serviceName = "HubService")
public class HubPortImpl implements HubPortType {

	/**
	 * The Endpoint manager controls the Web Service instance during its whole
	 * lifecycle.
	 */
	private HubEndpointManager endpointManager;

	/** Constructor receives a reference to the endpoint manager. */
	public HubPortImpl(HubEndpointManager endpointManager) {
		this.endpointManager = endpointManager;
	}

	// Main operations -------------------------------------------------------

	@Override
	public void activateAccount(String userId) throws InvalidUserIdFault_Exception {
		UDDINaming uddiNaming = endpointManager.getUddiNaming();

		Hub hub = Hub.getInstance();
		try {
			Collection<String> pts = uddiNaming.list("A40_Points1");

			List<PointsClient> ptsClients = new ArrayList<PointsClient>();
			System.out.println("IM HERE");
			for (String s : pts) {
				ptsClients.add(new PointsClient(s));
			}

			System.out.println("IM HERE2");
			PointsClient client = ptsClients.get(0);

			client.activateUser(userId);
			System.out.println("IM HERE 3");

			hub.activateUser(userId);

		} catch (UDDINamingException e) {
			throw new RuntimeException();
		} catch (PointsClientException e) {
			throw new RuntimeException();
		} catch (EmailAlreadyExistsFault_Exception e) {
			throw new InvalidUserIdFault_Exception("Email already exists", null);
		} catch (InvalidEmailFault_Exception e) {
			throw new InvalidUserIdFault_Exception("Invalid email", null);
		}
	}

	@Override
	public void loadAccount(String userId, int moneyToAdd, String creditCardNumber)
			throws InvalidCreditCardFault_Exception, InvalidMoneyFault_Exception, InvalidUserIdFault_Exception {
		UDDINaming uddiNaming = endpointManager.getUddiNaming();

		try {
			Collection<String> pts = uddiNaming.list("A40_Points1");

			List<PointsClient> ptsClients = new ArrayList<PointsClient>();

			for (String s : pts) {
				ptsClients.add(new PointsClient(s));
			}

			PointsClient client = ptsClients.get(0);

			// TODO check if credit card is okay!

			int pointsToAdd = 0;
			switch(moneyToAdd) {
				case 10:
					pointsToAdd = 1000;
					break;
				case 20:
					pointsToAdd = 2100;
					break;
				case 30:
					pointsToAdd = 3300;
					break;
				case 50:
					pointsToAdd = 5500;
					break;
				default:
					throw new InvalidMoneyFault_Exception("Invalid amount", null);
			}


			client.addPoints(userId, pointsToAdd);

		} catch (UDDINamingException e) {
			e.printStackTrace(); // TODO
		} catch (PointsClientException e) {
			e.printStackTrace(); //TODO
		} catch (InvalidPointsFault_Exception e) {
			throw new InvalidMoneyFault_Exception("Invalid amount", null);
		} catch (InvalidEmailFault_Exception e) {
			throw new InvalidUserIdFault_Exception("Invalid email", null);
		}
		
	}

	
	@Override
	public List<Food> searchDeal(String description) throws InvalidTextFault_Exception {
		UDDINaming uddiNaming = endpointManager.getUddiNaming();

		List<Food> searchedFoods = new ArrayList<Food>();
		List<Menu> searchedMenus = new ArrayList<Menu>();
		try {
			Collection<String> restaurants = uddiNaming.list("A40_Restaurant%");
			List<RestaurantClient> clients  = new ArrayList<RestaurantClient>();

			for (String rstId : restaurants) {
				RestaurantClient currRestClient = new RestaurantClient(rstId);
				clients.add(currRestClient);
			}
			for (RestaurantClient client : clients) {
				String restName = client.ctrlPing(null);
				searchedMenus = client.searchMenus(description);
				for (Menu menu : searchedMenus) {
					searchedFoods.add(createFood(menu, restName));
				}
			} 
			searchedFoods.sort(Comparator.comparing(Food::getPrice));
			return searchedFoods;
		} catch (UDDINamingException e) {
			e.printStackTrace();
		} catch (RestaurantClientException e) {
			e.printStackTrace();
		} catch (BadTextFault_Exception e) {
			throwInvalidTextException(e);
		}
		return null;
	} 

	@Override
	public List<Food> searchHungry(String description) throws InvalidTextFault_Exception {
		UDDINaming uddiNaming = endpointManager.getUddiNaming();

		List<Food> searchedFoods = new ArrayList<Food>();
		List<Menu> searchedMenus = new ArrayList<Menu>();
		try {
			Collection<String> restaurants = uddiNaming.list("A40_Restaurant%");
			List<RestaurantClient> clients  = new ArrayList<RestaurantClient>();

			for (String rstId : restaurants) {
				RestaurantClient currRestClient = new RestaurantClient(rstId);
				clients.add(currRestClient);
			}
			for (RestaurantClient client : clients) {
				String restName = client.ctrlPing(null);

				searchedMenus = client.searchMenus(description);
				for (Menu menu : searchedMenus) {
					searchedFoods.add(createFood(menu, restName));
				}
			} 
			searchedFoods.sort(Comparator.comparing(Food::getPreparationTime));
			return searchedFoods;
		} catch (UDDINamingException e) {
			throw new RuntimeException();
		} catch (RestaurantClientException e) {
			throw new RuntimeException();
		} catch (BadTextFault_Exception e) {
			throwInvalidTextException(e);
		}
		return null;
	}

	
	@Override
	public void addFoodToCart(String userId, FoodId foodId, int foodQuantity) throws InvalidFoodIdFault_Exception, InvalidFoodQuantityFault_Exception, InvalidUserIdFault_Exception {
		Hub hub = Hub.getInstance();
		try {
			com.forkexec.hub.domain.FoodId _foodId = new com.forkexec.hub.domain.FoodId(foodId.getRestaurantId(), foodId.getMenuId());
			hub.addFoodToCart(userId, _foodId, foodQuantity);
		} catch(com.forkexec.hub.domain.InvalidUserIdFault_Exception e) {
			throw new InvalidUserIdFault_Exception("Invalid User Id", null);
		} catch(com.forkexec.hub.domain.InvalidFoodQuantityFault_Exception e) {
			throw new InvalidFoodIdFault_Exception("Invalid food quantity", null);
		} catch(com.forkexec.hub.domain.InvalidFoodIdFault_Exception e) {
			throw new InvalidFoodIdFault_Exception("Invalid Food Id", null);
		} 
		
	}

	@Override
	public void clearCart(String userId) throws InvalidUserIdFault_Exception {
		Hub hub = Hub.getInstance();
		try{
			hub.clearCart(userId);
		} catch(com.forkexec.hub.domain.InvalidUserIdFault_Exception e) {
			throw new InvalidUserIdFault_Exception(userId, null);
		}
	}

	@Override
	public FoodOrder orderCart(String userId)
			throws EmptyCartFault_Exception, InvalidUserIdFault_Exception, NotEnoughPointsFault_Exception {
		try {
			Hub hub = Hub.getInstance();
			com.forkexec.hub.domain.FoodOrder cart = hub.orderCart(userId);

			FoodOrder _cart = buildWSFoodOrder(cart);

			if (_cart.getItems().isEmpty()) {
				throw new EmptyCartFault_Exception("Cart is empty", null);
			}

			UDDINaming uddiNaming = endpointManager.getUddiNaming();

			int pointsToSpend = 0;
			for (FoodOrderItem f : _cart.getItems()) {
				int qty = f.getFoodQuantity();
				int price = 0;

				String restId = f.getFoodId().getRestaurantId();
				String menuId = f.getFoodId().getMenuId();

				Collection<String> restaurants = uddiNaming.list(restId);  

				Collection<RestaurantClient> clients  = new ArrayList<RestaurantClient>();

				for(String s : restaurants) {
					clients.add(new RestaurantClient(s));
				}

				for(RestaurantClient client : clients) {
					MenuId menuIdObj = new MenuId();
					menuIdObj.setId(menuId);

					price = client.getMenu(menuIdObj).getPrice();
				}
				
				pointsToSpend = pointsToSpend + qty * price;
			}
			
			Collection<String> pts = uddiNaming.list("A40_Points1");  

			List<PointsClient> ptsClients  = new ArrayList<PointsClient>();

			for(String s : pts) {
				ptsClients.add(new PointsClient(s));
			}

			PointsClient client = ptsClients.get(0);

			client.spendPoints(userId, pointsToSpend);


			for (FoodOrderItem f : _cart.getItems()) {
				int qty = f.getFoodQuantity();

				String restId = f.getFoodId().getRestaurantId();
				String menuId = f.getFoodId().getMenuId();

				Collection<String> restaurants = uddiNaming.list(restId);  

				Collection<RestaurantClient> clients  = new ArrayList<RestaurantClient>();

				for(String s : restaurants) {
					clients.add(new RestaurantClient(s));
				}

				for(RestaurantClient rclient : clients) {
					MenuId menuIdObj = new MenuId();
					menuIdObj.setId(menuId);

					rclient.orderMenu(menuIdObj, qty);
				}
			}
			clearCart(userId);
			return _cart;
		 
		} catch (com.forkexec.hub.domain.InvalidUserIdFault_Exception e) {
			throw new InvalidUserIdFault_Exception("Invalid User Id", null);
		} catch (com.forkexec.hub.domain.EmptyCartFault_Exception e) {
			throw new EmptyCartFault_Exception("Empty cart", null);
		} catch (InvalidEmailFault_Exception e) {
			throw new InvalidUserIdFault_Exception("Invalid User Id", null);
		} catch (InvalidPointsFault_Exception e) {
			throw new NotEnoughPointsFault_Exception("Invalid Points", null);
		} catch (NotEnoughBalanceFault_Exception e) {
			throw new NotEnoughPointsFault_Exception("Invalid Points", null);
		} catch (RestaurantClientException e) {
			throw new RuntimeException();
		} catch (BadMenuIdFault_Exception e) {
			e.printStackTrace(); //TODO
		} catch (UDDINamingException e) {
			throw new RuntimeException();
		} catch (PointsClientException e) {
			throw new RuntimeException();
		} catch (BadQuantityFault_Exception e) {
			throwBadQuantityException(e);
		} catch (InsufficientQuantityFault_Exception e) {
			throw new RuntimeException(); //TODO
		} 
		return null;
	}

	private void throwBadQuantityException(BadQuantityFault_Exception e) {
	}

	@Override
	public int accountBalance(String userId) throws InvalidUserIdFault_Exception {
	    UDDINaming uddiNaming = endpointManager.getUddiNaming();

		try {
			Collection<String> pts = uddiNaming.list("A40_Points1");

			List<PointsClient> ptsClients = new ArrayList<PointsClient>();

			for (String s : pts) {
				ptsClients.add(new PointsClient(s));
			}

			PointsClient client = ptsClients.get(0);

			return client.pointsBalance(userId);
		} catch (UDDINamingException e) {
			throw new RuntimeException();
		} catch (PointsClientException e) {
			throw new RuntimeException();
		} catch (InvalidEmailFault_Exception e) {
			throw new InvalidUserIdFault_Exception("Invalid email", null);
		}
	}

	@Override
	public Food getFood(FoodId foodId) throws InvalidFoodIdFault_Exception {
		String menuId = foodId.getMenuId();
		String restId = foodId.getRestaurantId();

		Menu menu = new Menu();

		UDDINaming uddiNaming = endpointManager.getUddiNaming();
		try {
			//Restaurants will be a collection with only one string, since we know the exact restId.
			//Nonetheless, for is used cuz collection doesnt have get method and iterator would be overkill
			Collection<String> restaurants = uddiNaming.list(restId);  

			Collection<RestaurantClient> clients  = new ArrayList<RestaurantClient>();

			for(String s : restaurants) {
				clients.add(new RestaurantClient(s));
			}

			for(RestaurantClient client : clients) {
				MenuId id = new MenuId();
				id.setId(menuId);
				menu = client.getMenu(id);
			}

			return createFood(menu, restId);

		} catch (UDDINamingException e) {
			throw new RuntimeException();
		} catch (RestaurantClientException e) {
			throw new RuntimeException();
		} catch (BadMenuIdFault_Exception e) {
			throwInvalidFoodIdException(e);
		}

		return null;
	}

	@Override
	public List<FoodOrderItem> cartContents(String userId) throws InvalidUserIdFault_Exception {
		Hub hub = Hub.getInstance();

		try {
			com.forkexec.hub.domain.FoodOrder cart = hub.cartContents(userId);

			FoodOrder _cart = buildWSFoodOrder(cart);
			
			return _cart.getItems();
		} catch(com.forkexec.hub.domain.InvalidUserIdFault_Exception e) {
			throw new InvalidUserIdFault_Exception("Invalid User Id", null);
		}
	}

	// Control operations ----------------------------------------------------

	/** Diagnostic operation to check if service is running. */
	@Override
	public String ctrlPing(String inputMessage) {
		UDDINaming uddiNaming = endpointManager.getUddiNaming();
		try {
			Collection<String> restaurants = uddiNaming.list("A40_Restaurant%");
			Collection<RestaurantClient> clients  = new ArrayList<RestaurantClient>();

			StringBuilder msg = new StringBuilder();

			for(String s : restaurants) {
				clients.add(new RestaurantClient(s));
			}

			for(RestaurantClient r : clients) {
				msg.append(r.ctrlPing(inputMessage) + "||");
			}
			return msg.toString();
		} catch (UDDINamingException e) {
			e.printStackTrace();
		} catch (RestaurantClientException e) {
			e.printStackTrace();
		}

		return null;
	}

	/** Return all variables to default values. */
	@Override
	public void ctrlClear() {
		UDDINaming uddiNaming = endpointManager.getUddiNaming();
		try {
			Collection<String> restaurants = uddiNaming.list("A40_Restaurant%");
			Collection<RestaurantClient> clients  = new ArrayList<RestaurantClient>();

			Collection<String> points = uddiNaming.list("A40_Points1");
			Collection<PointsClient> pointsClients  = new ArrayList<PointsClient>();

			for(String s : restaurants) {
				clients.add(new RestaurantClient(s));
			}
			for(String s : points) {
				pointsClients.add(new PointsClient(s));
			}

			for(RestaurantClient client : clients) {
				client.ctrlClear();
			}

			for(PointsClient client : pointsClients) {
				client.ctrlClear();
			}
			Hub hub = Hub.getInstance();
			hub.reset();
		} catch (UDDINamingException e) {
			e.printStackTrace(); //TODO
		} catch (RestaurantClientException e) {
			e.printStackTrace(); //TODO
		} catch (PointsClientException e) {
			e.printStackTrace(); //TODO
		}
	}

	/** Set variables with specific values. */
	@Override
	public void ctrlInitFood(List<FoodInit> initialFoods) throws InvalidInitFault_Exception {
		UDDINaming uddiNaming = endpointManager.getUddiNaming();
		
		Collection<String> restaurants = new HashSet<String>();
		
		ConcurrentHashMap<String, List<MenuInit>> menuInitsByRest = new ConcurrentHashMap<String, List<MenuInit>>(); 

		List<MenuInit> menuInits;

		//Initialize the restaurant Ids array with all available rest ids
		for (FoodInit foodInit : initialFoods) {
			String restId = getRestId(foodInit);
			restaurants.add(restId);
		} 

		//Initialize MenuInits array for each available rest id and associate it with the id through hashmap
		for(String restId : restaurants) {
			menuInits = new ArrayList<MenuInit>();
			for(FoodInit foodInit : initialFoods) {
				if (getRestId(foodInit).equals(restId)) {
					menuInits.add(createMenuInit(foodInit));
				}
			}
			menuInitsByRest.put(restId, menuInits);
		}
		
		//Using the hashmap key and value (rest id and menuInit list) initiate each restaurant with the respective menus
		for (ConcurrentHashMap.Entry<String, List<MenuInit>> entry : menuInitsByRest.entrySet()) {
			String rest = entry.getKey();
			List<MenuInit> menuInit = entry.getValue();
			try {
				Collection<String> restaurantsURL = uddiNaming.list(rest);

				//Restaurants will be a collection with only one string, since we know the exact restId.
				//Nonetheless, for is used cuz collection doesnt have get method and iterator would be overkill
				Collection<RestaurantClient> clients  = new ArrayList<RestaurantClient>();
	
				for(String s : restaurantsURL) {
					clients.add(new RestaurantClient(s));
				}
	
				for(RestaurantClient client : clients) {
					client.ctrlInit(menuInit);
				}
	
			} catch (UDDINamingException e) {
				e.printStackTrace(); //TODO
			} catch (RestaurantClientException e) {
				e.printStackTrace(); //TODO
			} catch (BadInitFault_Exception e) {
				throwInvalidInitException(e);
			}
		}
	}
	

	@Override
	public void ctrlInitUserPoints(int startPoints) throws InvalidInitFault_Exception {
		UDDINaming uddiNaming = endpointManager.getUddiNaming();
		try {
			Collection<String> points = uddiNaming.list("A40_Points1");
			Collection<PointsClient> clients  = new ArrayList<PointsClient>();

			for(String s : points) {
				clients.add(new PointsClient(s));
			}

			for(PointsClient client : clients) {
				client.ctrlInit(startPoints);
			}

		} catch (UDDINamingException e) {
			e.printStackTrace(); //TODO
		} catch (PointsClientException e) {
			e.printStackTrace(); //TODO
		} catch (com.forkexec.pts.ws.BadInitFault_Exception e) {
			throwInvalidFoodIdException(e);
		}
	}

	// View helpers ----------------------------------------------------------
	
	//Create Food Object given menu and restaurant id
	private Food createFood(Menu menu, String rstId) {
		Food food = new Food();
		FoodId foodId = new FoodId();

		foodId.setRestaurantId(rstId);
		foodId.setMenuId(menu.getId().getId());

		food.setPlate(menu.getPlate());
		food.setEntree(menu.getEntree());
		food.setDessert(menu.getDessert());
		food.setPrice(menu.getPrice());
		food.setPreparationTime(menu.getPreparationTime());
		food.setId(foodId);
		
		return food;
	}

	private String getRestId(FoodInit foodInit) {
		return foodInit.getFood().getId().getRestaurantId();
	}

	private MenuInit createMenuInit(FoodInit foodInit) {
		MenuInit menuInit = new MenuInit();
		
		menuInit.setQuantity(foodInit.getQuantity());

		Menu menu = new Menu();
		menu.setDessert(foodInit.getFood().getDessert());
		menu.setEntree(foodInit.getFood().getEntree());
		menu.setPlate(foodInit.getFood().getPlate());
		menu.setPrice(foodInit.getFood().getPrice());
		menu.setPreparationTime(foodInit.getFood().getPreparationTime());

		MenuId menuId = new MenuId();
		menuId.setId(foodInit.getFood().getId().getMenuId());

		menu.setId(menuId);

		menuInit.setMenu(menu);

		return menuInit;
	}

	private FoodId buildWSFoodId(com.forkexec.hub.domain.FoodId foodId) {
		FoodId _foodId = new FoodId();
		_foodId.setMenuId(foodId.getMenuId());
		_foodId.setRestaurantId(foodId.getRestaurantId());

		return _foodId;
	}

	private FoodOrderId buildWSFoodOrderId(com.forkexec.hub.domain.FoodOrderId foodOrderId) {
		FoodOrderId _foodOrderId = new FoodOrderId();
		_foodOrderId.setId(foodOrderId.getId());		

		return _foodOrderId;
	}

	private FoodOrderItem buildWSFoodOrderItem(com.forkexec.hub.domain.FoodOrderItem foodOrderItem) {
		FoodOrderItem _foodOrderItem = new FoodOrderItem();

		FoodId _foodId = buildWSFoodId(foodOrderItem.getFoodId());
		_foodOrderItem.setFoodId(_foodId);

		_foodOrderItem.setFoodQuantity(foodOrderItem.getFoodQuantity());

		return _foodOrderItem;
	}

	private FoodOrder buildWSFoodOrder(com.forkexec.hub.domain.FoodOrder order) {
		FoodOrder _order = new FoodOrder();

		FoodOrderId _foodOrderId = buildWSFoodOrderId(order.getFoodOrderId());
		_order.setFoodOrderId(_foodOrderId);

		for(com.forkexec.hub.domain.FoodOrderItem f : order.getItems()){
			FoodOrderItem _foodOrderItem = buildWSFoodOrderItem(f);

			_order.getItems().add(_foodOrderItem);
		}

		return _order;
	}

	
	// Exception helpers -----------------------------------------------------


	private void throwInvalidTextException(BadTextFault_Exception e) throws InvalidTextFault_Exception {
		InvalidTextFault faultInfo = new InvalidTextFault();
		faultInfo.message = e.getMessage();
		throw new InvalidTextFault_Exception(e.getMessage(), faultInfo);
	}

	private void throwInvalidInitException(BadInitFault_Exception e) throws InvalidInitFault_Exception {
		InvalidInitFault faultInfo = new InvalidInitFault();
		faultInfo.message = e.getMessage();
		throw new InvalidInitFault_Exception(e.getMessage(), faultInfo);
	}

	private void throwInvalidFoodIdException(BadMenuIdFault_Exception e) throws InvalidFoodIdFault_Exception {
		InvalidFoodIdFault faultInfo = new InvalidFoodIdFault();
		faultInfo.message = e.getMessage();
		throw new InvalidFoodIdFault_Exception(e.getMessage(), faultInfo);
	}

	private void throwInvalidFoodIdException(com.forkexec.pts.ws.BadInitFault_Exception e) throws InvalidInitFault_Exception {
		InvalidInitFault faultInfo = new InvalidInitFault();
		faultInfo.message = e.getMessage();
		throw new InvalidInitFault_Exception(e.getMessage(), faultInfo);
	}

}
