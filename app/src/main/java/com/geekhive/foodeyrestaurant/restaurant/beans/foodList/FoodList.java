
package com.geekhive.foodeyrestaurant.restaurant.beans.foodList;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodList {

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
