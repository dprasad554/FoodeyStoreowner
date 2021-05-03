
package com.geekhive.foodeyrestaurant.grocery.beans.groceryorderdetailhome;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("GCart")
    @Expose
    private GCart gCart;
    @SerializedName("User")
    @Expose
    private User user;
    @SerializedName("GCartDetail")
    @Expose
    private List<GCartDetail> gCartDetail = null;

    public GCart getGCart() {
        return gCart;
    }

    public void setGCart(GCart gCart) {
        this.gCart = gCart;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<GCartDetail> getGCartDetail() {
        return gCartDetail;
    }

    public void setGCartDetail(List<GCartDetail> gCartDetail) {
        this.gCartDetail = gCartDetail;
    }

}
