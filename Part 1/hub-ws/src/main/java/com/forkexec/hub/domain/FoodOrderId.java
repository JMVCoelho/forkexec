package com.forkexec.hub.domain;

public class FoodOrderId {
    private String id;

    public FoodOrderId(String id) {
        this.setId(id);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

}