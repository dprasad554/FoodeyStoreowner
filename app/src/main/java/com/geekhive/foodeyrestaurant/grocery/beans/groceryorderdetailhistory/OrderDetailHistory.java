
package com.geekhive.foodeyrestaurant.grocery.beans.groceryorderdetailhistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetailHistory {

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
