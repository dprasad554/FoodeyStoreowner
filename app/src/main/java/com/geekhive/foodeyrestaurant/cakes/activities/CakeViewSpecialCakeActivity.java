package com.geekhive.foodeyrestaurant.cakes.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.cakes.beans.cakeorderdetailhistory.OrderDetailHistory;
import com.geekhive.foodeyrestaurant.cakes.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.cakes.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.cakes.utils.SnackBar;
import com.geekhive.foodeyrestaurant.cakes.utils.WebServices;

public class CakeViewSpecialCakeActivity extends AppCompatActivity implements OnResponseListner {

    Toolbar mActionBarToolbar;
    ConnectionDetector mDetector;
    String cart_id;
    int position_;
    ImageView cakeImage;
    TextView cakeName,cakeType,cakeQTY,cakeMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cake_activity_view_special_cake);

        mActionBarToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Special Cake Detail");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cart_id = getIntent().getStringExtra("cart_id");

        cakeImage = findViewById(R.id.iv_cake_Image);
        cakeName = findViewById(R.id.tv_cake_name);
        cakeQTY = findViewById(R.id.tv_quantity_type);
        cakeMessage = findViewById(R.id.tv_message_cake);

        setValues();
        CallService();
    }

    private void setValues() {
        mDetector = new ConnectionDetector(this);
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).OrderDetailHistory(WebServices.Foodey_Services,
                    WebServices.ApiType.orderdetailhistory, cart_id);
        }
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSuccess) {
        if (apiType == WebServices.ApiType.orderdetailhistory){
            if (!isSuccess){
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            }else {
                OrderDetailHistory orderDetailHistory = (OrderDetailHistory) response;
                if (!isSuccess || orderDetailHistory == null){
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                }else {
                    cakeName.setText(orderDetailHistory.getOrder().getCakeCartDetail().get(position_).getCake().getProductName());
                    cakeQTY.setText(orderDetailHistory.getOrder().getCakeCartDetail().get(position_).getCake().getQuantity() + " "
                            + orderDetailHistory.getOrder().getCakeCartDetail().get(position_).getCake().getQuantityDetail());
                }
            }
        }
    }
}
