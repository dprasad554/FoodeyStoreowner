package com.geekhive.foodeyrestaurant.cakes.utils;


import com.geekhive.foodeyrestaurant.cakes.beans.cakecancelOrder.CancelOrder;
import com.geekhive.foodeyrestaurant.cakes.beans.cakeorderConfirm.OrderConfirmation;
import com.geekhive.foodeyrestaurant.cakes.beans.cakeorderdetailhistory.OrderDetailHistory;
import com.geekhive.foodeyrestaurant.cakes.beans.cakeorderpacked.OrderPacked;
import com.geekhive.foodeyrestaurant.cakes.beans.cakestorelogin.StoreLogin;
import com.geekhive.foodeyrestaurant.cakes.beans.cakestoreneworder.StoreNewOrder;
import com.geekhive.foodeyrestaurant.cakes.beans.cakestoreorderhistory.OrderHistory;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GitApi {

  @POST("cake_store_login")
  @Multipart
  Call<StoreLogin> storeLogin(@Part MultipartBody.Part email, @Part MultipartBody.Part password, @Part MultipartBody.Part firebase_id);

  @POST("cake_store_order")
  @Multipart
  Call<OrderHistory> orderHistory(@Part MultipartBody.Part store_id);

  @POST("cake_store_order_detail")
  @Multipart
  Call<OrderDetailHistory> orderDetailHistory(@Part MultipartBody.Part cart_id);

  @POST("cake_store_confirm_order")
  @Multipart
  Call<OrderConfirmation> getOrderConfirmation(@Part MultipartBody.Part cart_id, @Part MultipartBody.Part store_id);

  @POST("cake_store_cancel_order")
  @Multipart
  Call<CancelOrder> getCancelOrder(@Part MultipartBody.Part cart_id, @Part MultipartBody.Part store_id);

  @POST("cake_store_new_order")
  @Multipart
  Call<StoreNewOrder> storeNewOrder(@Part MultipartBody.Part store_id);

  @POST("cake_store_packed_order")
  @Multipart
  Call<OrderPacked> orderPacked(@Part MultipartBody.Part cart_id, @Part MultipartBody.Part store_id);

}
