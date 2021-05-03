package com.geekhive.foodeyrestaurant.restaurant.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.beans.qrcode.QrCodeAv;
import com.geekhive.foodeyrestaurant.restaurant.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.restaurant.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.restaurant.utils.Prefs;
import com.geekhive.foodeyrestaurant.restaurant.utils.SnackBar;
import com.geekhive.foodeyrestaurant.restaurant.utils.WebServices;
import com.squareup.picasso.Picasso;

public class QRCodeActivity extends AppCompatActivity implements OnResponseListner {

    ImageView qrImage;
    ConnectionDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_r_code);
        mDetector = new ConnectionDetector(this);
        qrImage = findViewById(R.id.qrImage);
        CallService();

    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).QrCodeAvailable(WebServices.Foodey_Services,
                    WebServices.ApiType.qrcode, Prefs.getUserId(this));//, token
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.qrcode) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {

                QrCodeAv qrCodeAv = (QrCodeAv) response;
                if (!isSucces || qrCodeAv == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (qrCodeAv.getResturant() != null){
                        if(!qrCodeAv.getResturant().getBarCode().isEmpty()){
                            Picasso.get()
                                    .load(WebServices.Foodey_Rest_Url + qrCodeAv.getResturant().getBarCode())
                                    .into(qrImage);
                        }
                    }
                }
            }
        }

    }
}
