
package com.geekhive.foodeyrestaurant.restaurant.beans.revenue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RevenueDetails {

    @SerializedName("Revenue")
    @Expose
    private Revenue revenue;

    public Revenue getRevenue() {
        return revenue;
    }

    public void setRevenue(Revenue revenue) {
        this.revenue = revenue;
    }

}
