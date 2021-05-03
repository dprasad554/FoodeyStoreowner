package com.geekhive.foodeyrestaurant.restaurant.utils;

public interface OnResponseListner<T> {
    void onResponse(T t, WebServices.ApiType apiType, boolean z);
}