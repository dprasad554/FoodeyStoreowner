
package com.geekhive.foodeyrestaurant.cakes.beans.cakestoreneworder;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreNewOrder {

    @SerializedName("Order")
    @Expose
    private List<Order> order = null;

    public List<Order> getOrder() {
        return order;
    }

    public void setOrder(List<Order> order) {
        this.order = order;
    }

}
