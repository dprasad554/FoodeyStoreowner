
package com.geekhive.foodeyrestaurant.restaurant.beans.qrcode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resturant {

    @SerializedName("bar_code")
    @Expose
    private String barCode;
    @SerializedName("name")
    @Expose
    private String name;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
