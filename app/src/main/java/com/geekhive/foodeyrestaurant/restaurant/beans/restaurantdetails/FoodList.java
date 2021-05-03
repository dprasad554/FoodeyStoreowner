
package com.geekhive.foodeyrestaurant.restaurant.beans.restaurantdetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodList {

    @SerializedName("FoodList")
    @Expose
    private List<FoodList_> foodList = null;

    public List<FoodList_> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<FoodList_> foodList) {
        this.foodList = foodList;
    }

}
