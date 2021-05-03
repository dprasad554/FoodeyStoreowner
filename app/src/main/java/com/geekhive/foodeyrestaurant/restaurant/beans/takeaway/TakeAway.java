package com.geekhive.foodeyrestaurant.restaurant.beans.takeaway;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TakeAway {
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
