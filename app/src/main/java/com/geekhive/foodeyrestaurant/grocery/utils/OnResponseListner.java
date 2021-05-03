package com.geekhive.foodeyrestaurant.grocery.utils;

public interface OnResponseListner<T> {
    void onResponse(T t, WebServices.ApiType apiType, boolean z);
}