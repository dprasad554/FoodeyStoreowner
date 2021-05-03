
package com.geekhive.foodeyrestaurant.restaurant.beans.neworderdetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NewOrderDetails {

    @SerializedName("Order")
    @Expose
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
