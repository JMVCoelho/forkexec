package com.forkexec.hub.domain;

import java.util.List;

public class FoodOrder {
    private FoodOrderId foodOrderId;
    private List<FoodOrderItem> items;

    public FoodOrder(FoodOrderId foodOrderId, List<FoodOrderItem> items) {
        this.setFoodOrderId(foodOrderId);
        this.setItems(items);
    }

    /**
     * @return the items
     */
    public List<FoodOrderItem> getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<FoodOrderItem> items) {
        this.items = items;
    }

    /**
     * @return the foodOrderId
     */
    public FoodOrderId getFoodOrderId() {
        return foodOrderId;
    }

    /**
     * @param foodOrderId the foodOrderId to set
     */
    public void setFoodOrderId(FoodOrderId foodOrderId) {
        this.foodOrderId = foodOrderId;
    }

    /**
     * @param foodId the foodId to add
     * @param foodQuantity the quantity to add
     */
    public void addFood(FoodId foodId, int foodQuantity) {
        this.items.add(new FoodOrderItem(foodId, foodQuantity));
    }

    /**
     * Clears list of items
     */
    public void clearCart() {
        this.items.clear();
    }

}