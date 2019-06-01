package com.forkexec.hub.domain;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Hub
 *
 * A restaurants hub server.
 *
 */
public class Hub {
	private Map<String, FoodOrder> carts = new ConcurrentHashMap<>();
	private AtomicInteger orderIdCounter = new AtomicInteger(0);

	// Singleton -------------------------------------------------------------

	/** Private constructor prevents instantiation from other classes. */
	private Hub() {
		// Initialization of default values
	}

	/**
	 * SingletonHolder is loaded on the first execution of Singleton.getInstance()
	 * or the first access to SingletonHolder.INSTANCE, not before.
	 */
	private static class SingletonHolder {
		private static final Hub INSTANCE = new Hub();
	}

	public static synchronized Hub getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public void addFoodToCart(String userId, FoodId foodId, int foodQuantity)
			throws InvalidFoodIdFault_Exception, InvalidFoodQuantityFault_Exception, InvalidUserIdFault_Exception {
		FoodOrder cart = carts.get(userId);

		if (cart == null) {
			throw new InvalidUserIdFault_Exception("Invalid User Id");
		}

		if (foodId.getRestaurantId().trim().length() == 0 || foodId.getMenuId().trim().length() == 0) {
			throw new InvalidFoodIdFault_Exception("Invalid food Id");
		}

		if (foodQuantity < 1) {
			throw new InvalidFoodQuantityFault_Exception("Invalid food quantity");
		}

		cart.addFood(foodId, foodQuantity);
	}

	public FoodOrder cartContents(String userId) throws InvalidUserIdFault_Exception {
		FoodOrder cart = carts.get(userId);
		if (cart == null) {
			throw new InvalidUserIdFault_Exception("Invalid Use Id");		
		} else {
			return cart;
		}
	}

	public void clearCart(String userId) throws InvalidUserIdFault_Exception {
		FoodOrder cart = carts.get(userId);
		if (cart == null) {
			throw new InvalidUserIdFault_Exception("Invalid User Id");
		} else {
			cart.clearCart();
		}
	}

	public FoodOrder orderCart(String userId)
			throws EmptyCartFault_Exception, InvalidUserIdFault_Exception {
		FoodOrder cart = carts.get(userId);

		if (cart == null) {
			throw new InvalidUserIdFault_Exception("Invalida User Id");
		}
		if (cart.getItems().isEmpty()) {
			throw new EmptyCartFault_Exception("Cart is empty");
		}
		return cart;
	}

	public void activateUser(String userId) {
		int orderId = orderIdCounter.incrementAndGet();

		FoodOrder emptyCart = new FoodOrder(new FoodOrderId(Integer.toString(orderId)), new ArrayList<FoodOrderItem>());
		carts.put(userId, emptyCart);
	}

	public void reset() {
		carts.clear();
		orderIdCounter.set(0);
	}
	
}
