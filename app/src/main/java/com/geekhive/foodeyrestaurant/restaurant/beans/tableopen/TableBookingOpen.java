package com.geekhive.foodeyrestaurant.restaurant.beans.tableopen;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TableBookingOpen {
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
