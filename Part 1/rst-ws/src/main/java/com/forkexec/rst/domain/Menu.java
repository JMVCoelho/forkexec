package com.forkexec.rst.domain;

public class Menu {

    private String id;
    //MenuId is only a string, so there's no need to encapsulate it in a separated classe.
    private String entree;
    private String plate;
    private String dessert;
    private int price;
    private int preparationTime;
    private int quantity; 
    // Menu quantity is defined by control init using the class MenuInt
    // MenuInit will receive a Menu Object + quantity integer.

    /**
     * Constructor for Menu
     * 
     * @param id
     * @param price
     * @param entree
     * @param plate
     * @param dessert
     * @param price
     * @param preparationTime
     * @param quantity
     */
    public Menu(String id, String entree, String plate, String dessert,
    int price, int preparationTime, int quantity) {
        this.id = id;
        this.entree = entree;
        this.plate = plate;
        this.dessert = dessert;
        this.price = price;
        this.preparationTime = preparationTime;
        this.quantity = quantity;
    }

    /**
     * Getter for id
     * 
     * @return the id
     */
    public String getMenuId() {
        return id;
    }

    /**
     * Setter for id
     * @param id the id to set
     */
    public void setMenuId(String id) {
        this.id = id;
    }

    /**
     * Getter for price
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Setter for price
     * @param price the price to set
     */
    public void setunitPrice(int price) {
        this.price = price;
    }


    /**
     * Getter for entree
     * @return the entree
     */
    public String getEntree() {
        return entree;
    }

    /**
     * Setter for entree
     * @param entree the entree to set
     */
    public void setEntree(String entree) {
        this.entree = entree;
    }

    /**
     * Getter for plate
     * @return the plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Setter for plate
     * @param plate the plate to set
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /**
     * Getter for dessert
     * @return the dessert
     */
    public String getDessert() {
        return dessert;
    }

    /**
     * Setter for dessert
     * @param dessert the dessert to set
     */
    public void setDessert(String dessert) {
        this.dessert = dessert;
    }

    /**
     * Getter for preparationTime
     * @return the preparationTime
     */
    public int getPreparationTime() {
        return preparationTime;
    }

    /**
     * Setter for prearationTime
     * @param preparationTime the preparationTime to set
     */
    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    /**
     * Getter for quantity
     * @return the quantity
     */
    public synchronized int getQuantity() {
        return quantity;
    }

    /**
     * Setter for quantity
     * @param quantity the quantity to set
     */
    public synchronized void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
		builder.append("MenuOrder [id=").append(id);
		builder.append(", price=").append(price);
        builder.append(", entree=").append(entree);
        builder.append(", plate=").append(plate);
        builder.append(", dessert=").append(dessert);
        builder.append(", preparationTime=").append(preparationTime);
        builder.append(", quantity=").append(quantity);
		builder.append("]");
		return builder.toString();
    }
}
