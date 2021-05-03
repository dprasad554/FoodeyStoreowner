
package com.geekhive.foodeyrestaurant.restaurant.beans.restaurantdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodList_ {

    @SerializedName("Food")
    @Expose
    private Food food;

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

}
