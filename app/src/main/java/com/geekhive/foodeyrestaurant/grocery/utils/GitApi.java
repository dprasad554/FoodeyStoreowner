package com.geekhive.foodeyrestaurant.grocery.utils;


import com.geekhive.foodeyrestaurant.grocery.beans.grocerycancelOrder.CancelOrder;
import com.geekhive.foodeyrestaurant.grocery.beans.groceryorderConfirm.OrderConfirmation;
import com.geekhive.foodeyrestaurant.grocery.beans.groceryorderdetailhistory.OrderDetailHistory;
import com.geekhive.foodeyrestaurant.grocery.beans.groceryorderpacked.OrderPacked;
import com.geekhive.foodeyrestaurant.grocery.beans.grocerystorelogin.StoreLogin;
import com.geekhive.foodeyrestaurant.grocery.beans.grocerystoreneworder.StoreNewOrder;
import com.geekhive.foodeyrestaurant.grocery.beans.grocerystoreorderhistory.OrderHistory;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GitApi {

  @POST("store_login")
  @Multipart
  Call<StoreLogin> storeLogin(@Part MultipartBody.Part email, @Part MultipartBody.Part password, @Part MultipartBody.Part firebase_id);

  @POST("store_order")
  @Multipart
  Call<OrderHistory> orderHistory(@Part MultipartBody.Part store_id);

  @POST("store_order_detail")
  @Multipart
  Call<OrderDetailHistory> orderDetailHistory(@Part MultipartBody.Part cart_id);

  @POST("store_confirm_order")
  @Multipart
  Call<OrderConfirmation> getOrderConfirmation(@Part MultipartBody.Part cart_id, @Part MultipartBody.Part store_id);

  @POST("store_cancel_order")
  @Multipart
  Call<CancelOrder> getCancelOrder(@Part MultipartBody.Part cart_id, @Part MultipartBody.Part store_id);

  @POST("store_new_order")
  @Multipart
  Call<StoreNewOrder> storeNewOrder(@Part MultipartBody.Part store_id);

  @POST("store_packed_order")
  @Multipart
  Call<OrderPacked> orderPacked(@Part MultipartBody.Part cart_id, @Part MultipartBody.Part store_id);

}
