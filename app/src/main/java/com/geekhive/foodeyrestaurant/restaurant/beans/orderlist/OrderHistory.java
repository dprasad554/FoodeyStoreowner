
package com.geekhive.foodeyrestaurant.restaurant.beans.orderlist;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHistory {

    @SerializedName("Cart")
    @Expose
    private Cart cart;
    @SerializedName("User")
    @Expose
    private User user;
    @SerializedName("DeliveryBoy")
    @Expose
    private DeliveryBoy deliveryBoy;
    @SerializedName("CartDetail")
    @Expose
    private List<CartDetail> cartDetail = null;

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DeliveryBoy getDeliveryBoy() {
        return deliveryBoy;
    }

    public void setDeliveryBoy(DeliveryBoy deliveryBoy) {
        this.deliveryBoy = deliveryBoy;
    }

    public List<CartDetail> getCartDetail() {
        return cartDetail;
    }

    public void setCartDetail(List<CartDetail> cartDetail) {
        this.cartDetail = cartDetail;
    }

}
