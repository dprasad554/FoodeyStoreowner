
package com.geekhive.foodeyrestaurant.restaurant.beans.login;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginOut {

    @SerializedName("Resturant")
    @Expose
    private Resturant resturant;
    @SerializedName("Food")
    @Expose
    private List<Food> food = null;
    @SerializedName("ComboOffer")
    @Expose
    private List<Object> comboOffer = null;
    @SerializedName("Offer")
    @Expose
    private List<Object> offer = null;

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Resturant getResturant() {
        return resturant;
    }

    public void setResturant(Resturant resturant) {
        this.resturant = resturant;
    }

    public List<Food> getFood() {
        return food;
    }

    public void setFood(List<Food> food) {
        this.food = food;
    }

    public List<Object> getComboOffer() {
        return comboOffer;
    }

    public void setComboOffer(List<Object> comboOffer) {
        this.comboOffer = comboOffer;
    }

    public List<Object> getOffer() {
        return offer;
    }

    public void setOffer(List<Object> offer) {
        this.offer = offer;
    }

}
