package com.forkexec.hub.domain;

public class Food {
    private FoodId id;
    private String entree;
    private String plate;
    private String dessert;
    private int price;
    private int preparationTime;

    public Food(FoodId id, String entree, String plate, String dessert, int price, int preparationTime) {
        this.setId(id);
        this.setEntree(entree);
        this.setPlate(plate);
        this.setDessert(dessert);
        this.setPrice(price);
        this.setPreparationTime(preparationTime);
    }

    /**
     * @return the preparationTime
     */
    public int getPreparationTime() {
        return preparationTime;
    }

    /**
     * @param preparationTime the preparationTime to set
     */
    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    /**
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * @return the dessert
     */
    public String getDessert() {
        return dessert;
    }

    /**
     * @param dessert the dessert to set
     */
    public void setDessert(String dessert) {
        this.dessert = dessert;
    }

    /**
     * @return the plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * @param plate the plate to set
     */
    public void setPlate(String plate) {
        this.plate = plate;
    }

    /**
     * @return the entree
     */
    public String getEntree() {
        return entree;
    }

    /**
     * @param entree the entree to set
     */
    public void setEntree(String entree) {
        this.entree = entree;
    }

    /**
     * @return the id
     */
    public FoodId getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(FoodId id) {
        this.id = id;
    }

    
}