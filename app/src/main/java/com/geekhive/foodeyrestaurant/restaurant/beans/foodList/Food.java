
package com.geekhive.foodeyrestaurant.restaurant.beans.foodList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Food {

    @SerializedName("Cart")
    @Expose
    private Cart cart;
    @SerializedName("Food")
    @Expose
    private Food_ food;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Food_ getFood() {
        return food;
    }

    public void setFood(Food_ food) {
        this.food = food;
    }

}
