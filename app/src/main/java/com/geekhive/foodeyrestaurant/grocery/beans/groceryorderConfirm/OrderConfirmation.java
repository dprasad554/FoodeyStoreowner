package com.geekhive.foodeyrestaurant.grocery.beans.groceryorderConfirm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderConfirmation {

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
