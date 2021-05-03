
package com.geekhive.foodeyrestaurant.restaurant.beans.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Cart {

    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("user_id")
    @Expose
    private Object userId;
    @SerializedName("food_id")
    @Expose
    private Object foodId;
    @SerializedName("res_id")
    @Expose
    private Object resId;
    @SerializedName("del_id")
    @Expose
    private Object delId;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("delivery")
    @Expose
    private Object delivery;
    @SerializedName("discount")
    @Expose
    private Object discount;
    @SerializedName("totel")
    @Expose
    private Object totel;
    @SerializedName("date")
    @Expose
    private Object date;
    @SerializedName("time")
    @Expose
    private Object time;
    @SerializedName("order_id")
    @Expose
    private Object orderId;
    @SerializedName("payment_status")
    @Expose
    private Object paymentStatus;
    @SerializedName("address_id")
    @Expose
    private Object addressId;
    @SerializedName("store_id")
    @Expose
    private Object storeId;
    @SerializedName("order_type")
    @Expose
    private Object orderType;
    @SerializedName("order_status")
    @Expose
    private Object orderStatus;
    @SerializedName("cancel_by")
    @Expose
    private Object cancelBy;
    @SerializedName("tax")
    @Expose
    private Object tax;
    @SerializedName("grand_total")
    @Expose
    private Object grandTotal;
    @SerializedName("packing_charge")
    @Expose
    private Object packingCharge;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Object getFoodId() {
        return foodId;
    }

    public void setFoodId(Object foodId) {
        this.foodId = foodId;
    }

    public Object getResId() {
        return resId;
    }

    public void setResId(Object resId) {
        this.resId = resId;
    }

    public Object getDelId() {
        return delId;
    }

    public void setDelId(Object delId) {
        this.delId = delId;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getDelivery() {
        return delivery;
    }

    public void setDelivery(Object delivery) {
        this.delivery = delivery;
    }

    public Object getDiscount() {
        return discount;
    }

    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    public Object getTotel() {
        return totel;
    }

    public void setTotel(Object totel) {
        this.totel = totel;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public Object getTime() {
        return time;
    }

    public void setTime(Object time) {
        this.time = time;
    }

    public Object getOrderId() {
        return orderId;
    }

    public void setOrderId(Object orderId) {
        this.orderId = orderId;
    }

    public Object getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Object paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Object getAddressId() {
        return addressId;
    }

    public void setAddressId(Object addressId) {
        this.addressId = addressId;
    }

    public Object getStoreId() {
        return storeId;
    }

    public void setStoreId(Object storeId) {
        this.storeId = storeId;
    }

    public Object getOrderType() {
        return orderType;
    }

    public void setOrderType(Object orderType) {
        this.orderType = orderType;
    }

    public Object getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Object orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Object getCancelBy() {
        return cancelBy;
    }

    public void setCancelBy(Object cancelBy) {
        this.cancelBy = cancelBy;
    }

    public Object getTax() {
        return tax;
    }

    public void setTax(Object tax) {
        this.tax = tax;
    }

    public Object getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(Object grandTotal) {
        this.grandTotal = grandTotal;
    }

    public Object getPackingCharge() {
        return packingCharge;
    }

    public void setPackingCharge(Object packingCharge) {
        this.packingCharge = packingCharge;
    }

}
