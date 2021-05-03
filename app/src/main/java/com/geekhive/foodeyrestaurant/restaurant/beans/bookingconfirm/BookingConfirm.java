package com.geekhive.foodeyrestaurant.restaurant.beans.bookingconfirm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookingConfirm {
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
