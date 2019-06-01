package com.forkexec.rst.domain;

public class MenuOrder {
    
    private String menuId;
    private int menuQuantity;
    private String id;
    //OrderId is only a string, so there's no need to encapsulate it in a separated classe.


    public MenuOrder(String menuId, int menuQuantity, String id) {
        this.menuId = menuId;
        this.menuQuantity = menuQuantity;
        this.id = id;
    }

    /**
     * Getter for menuId
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

    /**
     * Getter for menuQuantity
     * @return the menuQuantity
     */
    public int getMenuQuantity() {
        return menuQuantity;
    }

    /**
     * Setter for menuQuantity
     * @param menuQuantity the menuQuantity to set
     */
    public void setMenuQuantity(int menuQuantity) {
        this.menuQuantity = menuQuantity;
    }

    /**
     * Getter for id
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for id
     * @param orderId the orderId to set
     */
    public void setOrderId(String id) {
        this.id = id;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
		builder.append("MenuOrder [menuId=").append(menuId);
		builder.append(", menuQuantity=").append(menuQuantity);
		builder.append(", id=").append(id);
		builder.append("]");
		return builder.toString();
    }

}