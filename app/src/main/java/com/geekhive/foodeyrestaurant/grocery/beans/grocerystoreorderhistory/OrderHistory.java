
package com.geekhive.foodeyrestaurant.grocery.beans.grocerystoreorderhistory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHistory {

    @SerializedName("OrderDetail")
    @Expose
    private List<OrderDetail> orderDetail = null;

    public List<OrderDetail> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetail> orderDetail) {
        this.orderDetail = orderDetail;
    }

}
