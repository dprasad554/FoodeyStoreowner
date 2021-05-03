package com.geekhive.foodeyrestaurant.restaurant.utils;

import android.app.Activity;
import android.app.IntentService;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WebServices<T> {

    public static String Foodey_Services = "http://foodeyservices.com/WebServices/";
    public static String Foodey_Image_Url = "http://foodeyservices.com/img/";
    public static String Foodey_Rest_Url = "http://foodeyservices.com/img/resturant/";
    public static String Foodey_Offer_Url = "http://foodeyservices.com/img/offer/";
    public static String Foodey_Food_Url = "http://foodeyservices.com/img/food/";
    public static String FoodeyService = Foodey_Services;
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
    public void Login(String api, final ApiType apiTypes, String email, String password, String firebase_id){//,
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
        call = (Call<T>) gi.getLoginDetails(MultipartBody.Part.createFormData("email", email), MultipartBody.Part.createFormData("password", password), MultipartBody.Part.createFormData("firebase_id", firebase_id));//,MultipartBody.Part.createFormData("firebase_id", firebase_id)
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

    //OrderList
    public void OrderList(String api, final ApiType apiTypes, String res_id){
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
        call = (Call<T>) gi.orderList(MultipartBody.Part.createFormData("res_id", res_id));
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

    //NewOrderList
    public void NewOrderList(String api, final ApiType apiTypes, String res_id){
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
        call = (Call<T>) gi.newOrderList(MultipartBody.Part.createFormData("res_id", res_id));
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

    //NewOrderDetails
    public void NewOrderDetails(String api, final ApiType apiTypes, String cart_id){
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
        call = (Call<T>) gi.getNewOrderDetails(MultipartBody.Part.createFormData("cart_id", cart_id));
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

    //RestaurantStatus
    public void RestaurantStatus(String api, final ApiType apiTypes, String res_id, String open_status){
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
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(api).addConverterFactory(GsonConverterFactory.create(gson)).client(builder.build()).build();
        GitApi gi = retrofit.create(GitApi.class);
        call = (Call<T>) gi.getRestaurantStatus(MultipartBody.Part.createFormData("res_id", res_id), MultipartBody.Part.createFormData("open_status", open_status));
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

    //Table Status
    public void TableBookingStatus(String api, final ApiType apiTypes, String res_id, String book_status){
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
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(api).addConverterFactory(GsonConverterFactory.create(gson)).client(builder.build()).build();
        GitApi gi = retrofit.create(GitApi.class);
        call = (Call<T>) gi.getTableStatus(MultipartBody.Part.createFormData("res_id", res_id), MultipartBody.Part.createFormData("book_status", book_status));
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

    //Take Away
    public void TakeAwayStatus(String api, final ApiType apiTypes, String res_id, String take_away){
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
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(api).addConverterFactory(GsonConverterFactory.create(gson)).client(builder.build()).build();
        GitApi gi = retrofit.create(GitApi.class);
        call = (Call<T>) gi.getTakeAway(MultipartBody.Part.createFormData("res_id", res_id), MultipartBody.Part.createFormData("take_away", take_away));
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

    //Sleep Mode
    public void SleepModeStatus(String api, final ApiType apiTypes, String res_id, String sleep_mode){
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
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(api).addConverterFactory(GsonConverterFactory.create(gson)).client(builder.build()).build();
        GitApi gi = retrofit.create(GitApi.class);
        call = (Call<T>) gi.getSleepMode(MultipartBody.Part.createFormData("res_id", res_id), MultipartBody.Part.createFormData("sleep_mode", sleep_mode));
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


    //FoodStatus
    public void FoodStatus(String api, final ApiType apiTypes, String id, String status){
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
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(api).addConverterFactory(GsonConverterFactory.create(gson)).client(builder.build()).build();
        GitApi gi = retrofit.create(GitApi.class);
        call = (Call<T>) gi.getFoodStatus(MultipartBody.Part.createFormData("id", id), MultipartBody.Part.createFormData("status", status));
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

    //Restaurant List
    public void RestaurantDetails(String api, final ApiType apiTypes, String res_id){
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
        call = (Call<T>) gi.getFoodList(MultipartBody.Part.createFormData("res_id", res_id));
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

    //Category List
    public void CategoryList(String api, final ApiType apiTypes){
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
        call = (Call<T>) gi.getCategoryList();
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

    //Add Menu
    public void AddMenu(String api, final ApiType apiTypes, String res_id, String name, String type, String category,
                        String food_time, String mrp, String price, String fileName, String filePath){
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

        File file = new File(filePath);
        byte[] bytes = new byte[0];
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            while (true) {
                int readNum = fis.read(buf);
                if (readNum == -1) {
                    break;
                }
                bos.write(buf, 0, readNum);
                System.out.println("read " + readNum + " bytes,");
            }
            bytes = bos.toByteArray();
            fis.close();
            bos.flush();
            bos.close();
        } catch (IOException e2) {
        }


        RequestBody fbody = RequestBody.create(MediaType.parse("image/*"), file);

        call = (Call<T>) gi.getAddMenu(MultipartBody.Part.createFormData("res_id", res_id),
                MultipartBody.Part.createFormData("name", name),
                MultipartBody.Part.createFormData("type", type),
                MultipartBody.Part.createFormData("category", category),
                MultipartBody.Part.createFormData("food_time", food_time),
                MultipartBody.Part.createFormData("mrp", mrp),
                MultipartBody.Part.createFormData("price", price),
                MultipartBody.Part.createFormData("image", fileName, fbody));
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

    //Food List
    public void FoodListData(String api, final ApiType apiTypes, String order_id){
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
        call = (Call<T>) gi.getFoodListArray(MultipartBody.Part.createFormData("order_id", order_id));
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
    public void ConfirmOrder(String api, final ApiType apiTypes, String cart_id, String res_id){
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
        call = (Call<T>) gi.getOrderConfirmation(MultipartBody.Part.createFormData("cart_id", cart_id), MultipartBody.Part.createFormData("res_id", res_id));
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
    public void CancelOrder(String api, final ApiType apiTypes, String cart_id, String res_id){
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
        call = (Call<T>) gi.getCancelOrder(MultipartBody.Part.createFormData("cart_id", cart_id), MultipartBody.Part.createFormData("res_id", res_id));
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

    //Revenue List
    public void RevenueList(String api, final ApiType apiTypes, String res_id){
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
        call = (Call<T>) gi.getRevenueDetails(MultipartBody.Part.createFormData("res_id", res_id));
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

    //RestaurantCategories
    public void RestaurantCategories(String api, final ApiType apiTypes, String res_id){
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
        call = (Call<T>) gi.getRestaurantCategories(MultipartBody.Part.createFormData("res_id", res_id));
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

    //RestaurantMenuList
    public void RestaurantMenuList(String api, final ApiType apiTypes, String res_id, String category){
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
        call = (Call<T>) gi.getRestaurantMenulist(MultipartBody.Part.createFormData("res_id", res_id),
                MultipartBody.Part.createFormData("category", category));
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

    //NotificationList
    public void NotificationList(String api, final ApiType apiTypes, String res_id){
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
        call = (Call<T>) gi.getNotificationList(MultipartBody.Part.createFormData("res_id", res_id));
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

    //BookingList
    public void BookingListRest(String api, final ApiType apiTypes, String res_id){
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
        call = (Call<T>) gi.getBookingList(MultipartBody.Part.createFormData("res_id", res_id));
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

    //BookingStatus
    public void BookingStatus(String api, final ApiType apiTypes, String res_id, String book_status, String booking_id){
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
        call = (Call<T>) gi.setBookingConfirm(MultipartBody.Part.createFormData("res_id", res_id),
                MultipartBody.Part.createFormData("book_status", book_status),
                MultipartBody.Part.createFormData("booking_id", booking_id));
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

    //BookingStatus
    public void QrCodeAvailable(String api, final ApiType apiTypes, String res_id){
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
        call = (Call<T>) gi.getQrCode(MultipartBody.Part.createFormData("res_id", res_id));
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
        products,
        verifyOtp,
        resendOtp,
        productCategoryList,
        productDetails,
        editProfile,
        restaurantList,
        restaurantStatus,
        tastatus,
        tbstatus,
        smstatus,
        foodStatus,
        confirmOrder,
        cancelOrder,
        newOrderList,
        foodList,
        addMenu,
        categoryList,
        revenuelist,
        notificationlist,
        neworderdetails,
        breakfastList,
        snackList,
        restaurantDetails,
        restaurantcategories,
        restaurantmenulist,
        addToCart,
        cartList,
        updateCartItem,
        removeCartItem,
        addAddress,
        addressList,
        placeOrder,
        orderHistory,
        sliderImages,
        selectService,
        checksum,
        storeList,
        selectStore,
        paytmresponse,
        bookinglist,
        bookstatus,
        qrcode
    }

}
