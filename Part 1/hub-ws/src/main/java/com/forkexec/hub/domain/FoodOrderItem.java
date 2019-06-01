package com.forkexec.hub.domain;

public class FoodOrderItem {
    private FoodId foodId;
    private int foodQuantity;

    public FoodOrderItem(FoodId foodId, int foodQuantity) {
        this.foodId = foodId;
        this.foodQuantity = foodQuantity;
    }

    /**
     * @return the foodId
     */
    public FoodId getFoodId() {
        return foodId;
    }

    /**
     * @param foodId the foodId to set
     */
    public void setFoodId(FoodId foodId) {
        this.foodId = foodId;
    }

    /**
     * @return the foodQuantity
     */
    public int getFoodQuantity() {
        return foodQuantity;
    }

    /**
     * @param foodQuantity the foodQuantity to set
     */
    public void setFoodQuantity(int foodQuantity) {
        this.foodQuantity = foodQuantity;
    }


}