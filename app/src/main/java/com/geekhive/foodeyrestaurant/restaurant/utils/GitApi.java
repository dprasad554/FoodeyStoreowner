package com.geekhive.foodeyrestaurant.restaurant.utils;

import com.geekhive.foodeyrestaurant.restaurant.beans.addmenu.AddMenu;
import com.geekhive.foodeyrestaurant.restaurant.beans.addmenu.CategoryList;
import com.geekhive.foodeyrestaurant.restaurant.beans.booking.BookingList;
import com.geekhive.foodeyrestaurant.restaurant.beans.bookingconfirm.BookingConfirm;
import com.geekhive.foodeyrestaurant.restaurant.beans.cancelOrder.CancelOrder;
import com.geekhive.foodeyrestaurant.restaurant.beans.categories.RestaurantCategory;
import com.geekhive.foodeyrestaurant.restaurant.beans.foodstatus.FoodStatus;
import com.geekhive.foodeyrestaurant.restaurant.beans.login.LoginOut;
import com.geekhive.foodeyrestaurant.restaurant.beans.neworder.Order;
import com.geekhive.foodeyrestaurant.restaurant.beans.neworderdetails.NewOrderDetails;
import com.geekhive.foodeyrestaurant.restaurant.beans.notification.Notifications;
import com.geekhive.foodeyrestaurant.restaurant.beans.orderConfirm.OrderConfirmation;
import com.geekhive.foodeyrestaurant.restaurant.beans.orderlist.OrderListOut;
import com.geekhive.foodeyrestaurant.restaurant.beans.qrcode.QrCodeAv;
import com.geekhive.foodeyrestaurant.restaurant.beans.restaurantdetails.FoodList;
import com.geekhive.foodeyrestaurant.restaurant.beans.restaurantmenulist.RestaurantMenuList;
import com.geekhive.foodeyrestaurant.restaurant.beans.restaurantstat.RestaurantStatus;
import com.geekhive.foodeyrestaurant.restaurant.beans.revenue.RevenueDetails;
import com.geekhive.foodeyrestaurant.restaurant.beans.sleepmode.SleepMode;
import com.geekhive.foodeyrestaurant.restaurant.beans.tableopen.TableBookingOpen;
import com.geekhive.foodeyrestaurant.restaurant.beans.takeaway.TakeAway;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GitApi {

    @POST("resturant_login")
    @Multipart
    Call<LoginOut> getLoginDetails(@Part MultipartBody.Part email, @Part MultipartBody.Part password, @Part MultipartBody.Part firebase_id);//,  @Part MultipartBody.Part firebase_id

    @POST("res_order_history")
    @Multipart
    Call<OrderListOut> orderList(@Part MultipartBody.Part res_id);

    @POST("new_order")
    @Multipart
    Call<Order> newOrderList(@Part MultipartBody.Part res_id);

    @POST("res_new_order")
    @Multipart
    Call<NewOrderDetails> getNewOrderDetails(@Part MultipartBody.Part cart_id);

    @POST("resturant_status")
    @Multipart
    Call<RestaurantStatus> getRestaurantStatus(@Part MultipartBody.Part res_id, @Part MultipartBody.Part open_status);

    @POST("resturant_tablebooking_open")
    @Multipart
    Call<TableBookingOpen> getTableStatus(@Part MultipartBody.Part res_id, @Part MultipartBody.Part book_status);

    @POST("sleep_mode")
    @Multipart
    Call<SleepMode> getSleepMode(@Part MultipartBody.Part res_id, @Part MultipartBody.Part sleep_mode);

    @POST("resturant_take_away")
    @Multipart
    Call<TakeAway> getTakeAway(@Part MultipartBody.Part res_id, @Part MultipartBody.Part take_away);

    @POST("food_list")
    @Multipart
    Call<FoodList> getFoodList(@Part MultipartBody.Part res_id);

    @POST("order_food_list")
    @Multipart
    Call<com.geekhive.foodeyrestaurant.restaurant.beans.foodList.FoodList> getFoodListArray(@Part MultipartBody.Part order_id);


    @POST("confirm_order")
    @Multipart
    Call<OrderConfirmation> getOrderConfirmation(@Part MultipartBody.Part cart_id, @Part MultipartBody.Part res_id);

    @POST("cancel_order")
    @Multipart
    Call<CancelOrder> getCancelOrder(@Part MultipartBody.Part cart_id, @Part MultipartBody.Part res_id);

    @POST("food_status")
    @Multipart
    Call<FoodStatus> getFoodStatus(@Part MultipartBody.Part id, @Part MultipartBody.Part status);

    @POST("resturant_add_food")
    @Multipart
    Call<AddMenu> getAddMenu(@Part MultipartBody.Part res_id, @Part MultipartBody.Part name, @Part MultipartBody.Part type,
                             @Part MultipartBody.Part category, @Part MultipartBody.Part food_time, @Part MultipartBody.Part mrp,
                             @Part MultipartBody.Part price, @Part MultipartBody.Part image);

    @GET("category")
    Call<CategoryList> getCategoryList();

    @POST("res_revenue")
    @Multipart
    Call<RevenueDetails> getRevenueDetails(@Part MultipartBody.Part res_id);

    @POST("res_category")
    @Multipart
    Call<RestaurantCategory> getRestaurantCategories(@Part MultipartBody.Part res_id);

    @POST("res_category_food")
    @Multipart
    Call<RestaurantMenuList> getRestaurantMenulist(@Part MultipartBody.Part res_id, @Part MultipartBody.Part category);

    @POST("notification_resturant_new")
    @Multipart
    Call<Notifications> getNotificationList(@Part MultipartBody.Part res_id);

    @POST("table_book_list")
    @Multipart
    Call<BookingList> getBookingList(@Part MultipartBody.Part res_id);

    @POST("resturant_table_booking_status")
    @Multipart
    Call<BookingConfirm> setBookingConfirm(@Part MultipartBody.Part res_id, @Part MultipartBody.Part book_status, @Part MultipartBody.Part booking_id);

    @POST("resturant_bar_code")
    @Multipart
    Call<QrCodeAv> getQrCode(@Part MultipartBody.Part res_id);
}
