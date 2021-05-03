
package com.geekhive.foodeyrestaurant.cakes.beans.cakestoreorderhistory;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {

    @SerializedName("CakeCart")
    @Expose
    private CakeCart cakeCart;
    @SerializedName("User")
    @Expose
    private User user;
    @SerializedName("CakeCartDetail")
    @Expose
    private List<CakeCartDetail> cakeCartDetail = null;

    public CakeCart getCakeCart() {
        return cakeCart;
    }

    public void setCakeCart(CakeCart cakeCart) {
        this.cakeCart = cakeCart;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CakeCartDetail> getCakeCartDetail() {
        return cakeCartDetail;
    }

    public void setCakeCartDetail(List<CakeCartDetail> cakeCartDetail) {
        this.cakeCartDetail = cakeCartDetail;
    }

}
