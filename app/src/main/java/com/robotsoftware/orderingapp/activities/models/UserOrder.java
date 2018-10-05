package com.robotsoftware.orderingapp.activities.models;

public class UserOrder {

    private String name;
    private String checked;
    private String quantity;
    private String price;

    /**
     * method for getter
     */

    public String getName() {
        return name;
    }

    /**
     * method for setter
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
