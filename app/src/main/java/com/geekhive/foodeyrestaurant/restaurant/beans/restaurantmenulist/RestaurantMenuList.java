
package com.geekhive.foodeyrestaurant.restaurant.beans.restaurantmenulist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RestaurantMenuList {

    @SerializedName("Food")
    @Expose
    private List<Food> food = null;

    public List<Food> getFood() {
        return food;
    }

    public void setFood(List<Food> food) {
        this.food = food;
    }

}
