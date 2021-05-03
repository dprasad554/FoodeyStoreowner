
package com.geekhive.foodeyrestaurant.restaurant.beans.qrcode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QrCodeAv {

    @SerializedName("Resturant")
    @Expose
    private Resturant resturant;

    public Resturant getResturant() {
        return resturant;
    }

    public void setResturant(Resturant resturant) {
        this.resturant = resturant;
    }

}
