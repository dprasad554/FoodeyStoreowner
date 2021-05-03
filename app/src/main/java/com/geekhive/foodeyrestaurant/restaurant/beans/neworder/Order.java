
package com.geekhive.foodeyrestaurant.restaurant.beans.neworder;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("Order")
    @Expose
    private List<Order_> order = null;

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Order_> getOrder() {
        return order;
    }

    public void setOrder(List<Order_> order) {
        this.order = order;
    }

}
