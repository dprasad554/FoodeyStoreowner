
package com.geekhive.foodeyrestaurant.restaurant.beans.orderlist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeliveryBoy {

    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("firstname")
    @Expose
    private Object firstname;
    @SerializedName("lastname")
    @Expose
    private Object lastname;
    @SerializedName("firebase_id")
    @Expose
    private Object firebaseId;
    @SerializedName("password")
    @Expose
    private Object password;
    @SerializedName("date")
    @Expose
    private Object date;
    @SerializedName("mobile")
    @Expose
    private Object mobile;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("login_status")
    @Expose
    private Object loginStatus;
    @SerializedName("otp")
    @Expose
    private Object otp;
    @SerializedName("id_proof")
    @Expose
    private Object idProof;
    @SerializedName("driving_license")
    @Expose
    private Object drivingLicense;
    @SerializedName("residance_proof")
    @Expose
    private Object residanceProof;
    @SerializedName("profile")
    @Expose
    private Object profile;
    @SerializedName("bike_number")
    @Expose
    private Object bikeNumber;
    @SerializedName("woking_status")
    @Expose
    private Object wokingStatus;
    @SerializedName("sub_admin_id")
    @Expose
    private Object subAdminId;
    @SerializedName("city_id")
    @Expose
    private Object cityId;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getFirstname() {
        return firstname;
    }

    public void setFirstname(Object firstname) {
        this.firstname = firstname;
    }

    public Object getLastname() {
        return lastname;
    }

    public void setLastname(Object lastname) {
        this.lastname = lastname;
    }

    public Object getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(Object firebaseId) {
        this.firebaseId = firebaseId;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(Object loginStatus) {
        this.loginStatus = loginStatus;
    }

    public Object getOtp() {
        return otp;
    }

    public void setOtp(Object otp) {
        this.otp = otp;
    }

    public Object getIdProof() {
        return idProof;
    }

    public void setIdProof(Object idProof) {
        this.idProof = idProof;
    }

    public Object getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(Object drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public Object getResidanceProof() {
        return residanceProof;
    }

    public void setResidanceProof(Object residanceProof) {
        this.residanceProof = residanceProof;
    }

    public Object getProfile() {
        return profile;
    }

    public void setProfile(Object profile) {
        this.profile = profile;
    }

    public Object getBikeNumber() {
        return bikeNumber;
    }

    public void setBikeNumber(Object bikeNumber) {
        this.bikeNumber = bikeNumber;
    }

    public Object getWokingStatus() {
        return wokingStatus;
    }

    public void setWokingStatus(Object wokingStatus) {
        this.wokingStatus = wokingStatus;
    }

    public Object getSubAdminId() {
        return subAdminId;
    }

    public void setSubAdminId(Object subAdminId) {
        this.subAdminId = subAdminId;
    }

    public Object getCityId() {
        return cityId;
    }

    public void setCityId(Object cityId) {
        this.cityId = cityId;
    }

}
