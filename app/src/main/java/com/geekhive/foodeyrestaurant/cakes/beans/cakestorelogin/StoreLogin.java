
package com.geekhive.foodeyrestaurant.cakes.beans.cakestorelogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreLogin {

    @SerializedName("CakeStore")
    @Expose
    private CakeStore cakeStore;

    public CakeStore getCakeStore() {
        return cakeStore;
    }

    public void setCakeStore(CakeStore cakeStore) {
        this.cakeStore = cakeStore;
    }

}
