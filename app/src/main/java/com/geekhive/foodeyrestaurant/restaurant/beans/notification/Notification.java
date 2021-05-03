
package com.geekhive.foodeyrestaurant.restaurant.beans.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("Notification")
    @Expose
    private Notification_ notification;
    @SerializedName("User")
    @Expose
    private User user;
    @SerializedName("Cart")
    @Expose
    private Cart cart;

    public Notification_ getNotification() {
        return notification;
    }

    public void setNotification(Notification_ notification) {
        this.notification = notification;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

}
