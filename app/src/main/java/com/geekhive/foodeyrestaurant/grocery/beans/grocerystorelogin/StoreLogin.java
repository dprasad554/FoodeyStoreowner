
package com.geekhive.foodeyrestaurant.grocery.beans.grocerystorelogin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StoreLogin {

    @SerializedName("Store")
    @Expose
    private Store store;

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

}
