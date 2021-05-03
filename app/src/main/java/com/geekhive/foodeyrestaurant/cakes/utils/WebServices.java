package com.geekhive.foodeyrestaurant.cakes.utils;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WebServices<T> {

  public static String Foodey_Services = "http://foodeyservices.com/WebServices/";
  public static String Foodey_Store_Image_URL = "http://foodeyservices.com/img/grocery/";
  private static OkHttpClient.Builder builder;

  ApiType apiTypeVariable;

  Call<T> call = null;
  Context context;

  OnResponseListner<T> onResponseListner;
  android.app.ProgressDialog pdLoading;
  private T t;

  public WebServices(OnResponseListner<T> onResponseListner) {
    if (onResponseListner instanceof Activity) {
      this.context = (Context) onResponseListner;
    } else if (onResponseListner instanceof IntentService) {
      this.context = (Context) onResponseListner;
    } else if (onResponseListner instanceof android.app.DialogFragment) {
      android.app.DialogFragment dialogFragment = (android.app.DialogFragment) onResponseListner;
      this.context = dialogFragment.getActivity();
    } else {
      android.support.v4.app.Fragment fragment = (android.support.v4.app.Fragment) onResponseListner;
      this.context = fragment.getActivity();
    }

    this.onResponseListner = onResponseListner;
    builder = getHttpClient();
  }

  public WebServices(Context context, OnResponseListner<T> onResponseListner) {
    this.onResponseListner = onResponseListner;
    this.context = context;
    builder = getHttpClient();
  }

  public T getT() {
    return t;
  }

  public void setT(T t) {
    this.t = t;
  }


  public OkHttpClient.Builder getHttpClient() {
    if (builder == null) {
      HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
      loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
      OkHttpClient.Builder client = new OkHttpClient.Builder();
      client.readTimeout(60, TimeUnit.SECONDS);
      client.connectTimeout(60, TimeUnit.SECONDS);
      client.addInterceptor(loggingInterceptor);
      return client;
    }
    return builder;
  }

  //Login
  public void Login(String api, final ApiType apiTypes, String email, String password, String firebase_id){
    ProgressDialog progressDialog = new ProgressDialog();
    try {
      this.pdLoading = progressDialog.getInstance(this.context);
      this.pdLoading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
      if (this.pdLoading.isShowing()) {
        this.pdLoading.cancel();
      }
      this.pdLoading.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.apiTypeVariable = apiTypes;

    Retrofit retrofit = new Retrofit.Builder().baseUrl(api).addConverterFactory(GsonConverterFactory.create()).client(builder.build()).build();
    GitApi gi = retrofit.create(GitApi.class);
    call = (Call<T>) gi.storeLogin(MultipartBody.Part.createFormData("email", email),
            MultipartBody.Part.createFormData("password", password),
            MultipartBody.Part.createFormData("firebase_id", firebase_id));//,MultipartBody.Part.createFormData("firebase_id", firebase_id)
    call.enqueue(new Callback<T>() {

      @Override
      public void onResponse(Call<T> call, Response<T> response) {
        t = (T) response.body();
        if (pdLoading.isShowing())
          pdLoading.cancel();
        onResponseListner.onResponse(t, apiTypeVariable, true);
      }

      @Override
      public void onFailure(Call<T> call, Throwable t) {
        Log.e(apiTypes.toString(), t.getMessage() + ".");
        if (pdLoading.isShowing())
          pdLoading.cancel();
        onResponseListner.onResponse(null, apiTypeVariable, false);
      }
    });
  }

  //For Store Order History
  public void StoreOrderHistory(String api, final ApiType apiTypes, String store_id){
    ProgressDialog progressDialog = new ProgressDialog();
    try {
      this.pdLoading = progressDialog.getInstance(this.context);
      this.pdLoading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
      if (this.pdLoading.isShowing()) {
        this.pdLoading.cancel();
      }
      this.pdLoading.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.apiTypeVariable = apiTypes;

    Retrofit retrofit = new Retrofit.Builder().baseUrl(api).addConverterFactory(GsonConverterFactory.create()).client(builder.build()).build();
    GitApi gi = retrofit.create(GitApi.class);
    call = (Call<T>) gi.orderHistory(MultipartBody.Part.createFormData("store_id", store_id));
    call.enqueue(new Callback<T>() {

      @Override
      public void onResponse(Call<T> call, Response<T> response) {
        t = (T) response.body();
        if (pdLoading.isShowing())
          pdLoading.cancel();
        onResponseListner.onResponse(t, apiTypeVariable, true);
      }

      @Override
      public void onFailure(Call<T> call, Throwable t) {
        Log.e(apiTypes.toString(), t.getMessage() + ".");
        if (pdLoading.isShowing())
          pdLoading.cancel();
        onResponseListner.onResponse(null, apiTypeVariable, false);
      }
    });
  }

  //For Store Order History
  public void OrderDetailHistory(String api, final ApiType apiTypes, String cart_id){
    ProgressDialog progressDialog = new ProgressDialog();
    try {
      this.pdLoading = progressDialog.getInstance(this.context);
      this.pdLoading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
      if (this.pdLoading.isShowing()) {
        this.pdLoading.cancel();
      }
      this.pdLoading.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.apiTypeVariable = apiTypes;

    Retrofit retrofit = new Retrofit.Builder().baseUrl(api).addConverterFactory(GsonConverterFactory.create()).client(builder.build()).build();
    GitApi gi = retrofit.create(GitApi.class);
    call = (Call<T>) gi.orderDetailHistory(MultipartBody.Part.createFormData("cart_id", cart_id));
    call.enqueue(new Callback<T>() {

      @Override
      public void onResponse(Call<T> call, Response<T> response) {
        t = (T) response.body();
        if (pdLoading.isShowing())
          pdLoading.cancel();
        onResponseListner.onResponse(t, apiTypeVariable, true);
      }

      @Override
      public void onFailure(Call<T> call, Throwable t) {
        Log.e(apiTypes.toString(), t.getMessage() + ".");
        if (pdLoading.isShowing())
          pdLoading.cancel();
        onResponseListner.onResponse(null, apiTypeVariable, false);
      }
    });
  }

  //Confirm Order
  public void ConfirmOrder(String api, final ApiType apiTypes, String cart_id, String store_id){
    ProgressDialog progressDialog = new ProgressDialog();
    try {
      this.pdLoading = progressDialog.getInstance(this.context);
      this.pdLoading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
      if (this.pdLoading.isShowing()) {
        this.pdLoading.cancel();
      }
      this.pdLoading.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.apiTypeVariable = apiTypes;
        /*Gson gson = new GsonBuilder()
                .setLenient()
                .create();*/

    Retrofit retrofit = new Retrofit.Builder().baseUrl(api).addConverterFactory(GsonConverterFactory.create()).client(builder.build()).build();
    GitApi gi = retrofit.create(GitApi.class);
    call = (Call<T>) gi.getOrderConfirmation(MultipartBody.Part.createFormData("cart_id", cart_id), MultipartBody.Part.createFormData("store_id", store_id));
    call.enqueue(new Callback<T>() {

      @Override
      public void onResponse(Call<T> call, Response<T> response) {
        t = (T) response.body();
        if (pdLoading.isShowing())
          pdLoading.cancel();
        onResponseListner.onResponse(t, apiTypeVariable, true);
      }

      @Override
      public void onFailure(Call<T> call, Throwable t) {
        Log.e(apiTypes.toString(), t.getMessage() + ".");
        if (pdLoading.isShowing())
          pdLoading.cancel();
        onResponseListner.onResponse(null, apiTypeVariable, false);
      }
    });
  }

  //Cancel Order
  public void CancelOrder(String api, final ApiType apiTypes, String cart_id, String store_id){
    ProgressDialog progressDialog = new ProgressDialog();
    try {
      this.pdLoading = progressDialog.getInstance(this.context);
      this.pdLoading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
      if (this.pdLoading.isShowing()) {
        this.pdLoading.cancel();
      }
      this.pdLoading.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.apiTypeVariable = apiTypes;
        /*Gson gson = new GsonBuilder()
                .setLenient()
                .create();*/

    Retrofit retrofit = new Retrofit.Builder().baseUrl(api).addConverterFactory(GsonConverterFactory.create()).client(builder.build()).build();
    GitApi gi = retrofit.create(GitApi.class);
    call = (Call<T>) gi.getCancelOrder(MultipartBody.Part.createFormData("cart_id", cart_id), MultipartBody.Part.createFormData("store_id", store_id));
    call.enqueue(new Callback<T>() {

      @Override
      public void onResponse(Call<T> call, Response<T> response) {
        t = (T) response.body();
        if (pdLoading.isShowing())
          pdLoading.cancel();
        onResponseListner.onResponse(t, apiTypeVariable, true);
      }

      @Override
      public void onFailure(Call<T> call, Throwable t) {
        Log.e(apiTypes.toString(), t.getMessage() + ".");
        if (pdLoading.isShowing())
          pdLoading.cancel();
        onResponseListner.onResponse(null, apiTypeVariable, false);
      }
    });
  }

  //For Store New Order
  public void StoreNewOrder(String api, final ApiType apiTypes, String store_id){
    ProgressDialog progressDialog = new ProgressDialog();
    try {
      this.pdLoading = progressDialog.getInstance(this.context);
      this.pdLoading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
      if (this.pdLoading.isShowing()) {
        this.pdLoading.cancel();
      }
      this.pdLoading.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.apiTypeVariable = apiTypes;

    Retrofit retrofit = new Retrofit.Builder().baseUrl(api).addConverterFactory(GsonConverterFactory.create()).client(builder.build()).build();
    GitApi gi = retrofit.create(GitApi.class);
    call = (Call<T>) gi.storeNewOrder(MultipartBody.Part.createFormData("store_id", store_id));
    call.enqueue(new Callback<T>() {

      @Override
      public void onResponse(Call<T> call, Response<T> response) {
        t = (T) response.body();
        if (pdLoading.isShowing())
          pdLoading.cancel();
        onResponseListner.onResponse(t, apiTypeVariable, true);
      }

      @Override
      public void onFailure(Call<T> call, Throwable t) {
        Log.e(apiTypes.toString(), t.getMessage() + ".");
        if (pdLoading.isShowing())
          pdLoading.cancel();
        onResponseListner.onResponse(null, apiTypeVariable, false);
      }
    });
  }

  public void OrderPacked(String api, final ApiType apiTypes, String cart_id, String store_id){
    ProgressDialog progressDialog = new ProgressDialog();
    try {
      this.pdLoading = progressDialog.getInstance(this.context);
      this.pdLoading.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
      if (this.pdLoading.isShowing()) {
        this.pdLoading.cancel();
      }
      this.pdLoading.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
    this.apiTypeVariable = apiTypes;

    Retrofit retrofit = new Retrofit.Builder().baseUrl(api).addConverterFactory(GsonConverterFactory.create()).client(builder.build()).build();
    GitApi gi = retrofit.create(GitApi.class);
    call = (Call<T>) gi.orderPacked(MultipartBody.Part.createFormData("cart_id", cart_id), MultipartBody.Part.createFormData("store_id", store_id));
    call.enqueue(new Callback<T>() {

      @Override
      public void onResponse(Call<T> call, Response<T> response) {
        t = (T) response.body();
        if (pdLoading.isShowing())
          pdLoading.cancel();
        onResponseListner.onResponse(t, apiTypeVariable, true);
      }

      @Override
      public void onFailure(Call<T> call, Throwable t) {
        Log.e(apiTypes.toString(), t.getMessage() + ".");
        if (pdLoading.isShowing())
          pdLoading.cancel();
        onResponseListner.onResponse(null, apiTypeVariable, false);
      }
    });
  }

  public enum ApiType {
    login,
    storeorderhistory,
    orderdetailhistory,
    confirmOrder,
    cancelOrder,
    storeneworder,
    orderpacked
  }
}
