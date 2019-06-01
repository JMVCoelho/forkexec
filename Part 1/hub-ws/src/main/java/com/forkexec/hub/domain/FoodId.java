package com.forkexec.hub.domain;

public class FoodId {
    private String restaurantId;
    private String menuId;

    public FoodId(String restaurantId, String menuId) {
        this.setRestaurantId(restaurantId);
        this.setMenuId(menuId);
    }

    /**
     * @return the restaurantId
     */
    public String getRestaurantId() {
        return restaurantId;
    }

    /**
     * @param restaurantId the restaurantId to set
     */
    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    /**
     * @return the menuId
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     * @param menuId the menuId to set
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }


}