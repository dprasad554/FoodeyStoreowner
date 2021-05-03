package com.geekhive.foodeyrestaurant.cakes.utils;

public interface OnResponseListner<T> {
    void onResponse(T t, WebServices.ApiType apiType, boolean z);
}