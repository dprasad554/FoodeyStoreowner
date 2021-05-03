package com.geekhive.foodeyrestaurant.grocery.beans.groceryorderpacked;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderPacked {

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}