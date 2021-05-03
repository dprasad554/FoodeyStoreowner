package com.geekhive.foodeyrestaurant.restaurant.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.adapters.OrdersAdapter;
import com.geekhive.foodeyrestaurant.restaurant.beans.revenue.RevenueDetails;
import com.geekhive.foodeyrestaurant.restaurant.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.restaurant.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.restaurant.utils.Prefs;
import com.geekhive.foodeyrestaurant.restaurant.utils.SnackBar;
import com.geekhive.foodeyrestaurant.restaurant.utils.WebServices;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrdersActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListner {

    @BindView(R.id.vI_aac_revenue_back)
    ImageView vI_aac_revenue_back;
    @BindView(R.id.revenueItems)
    RecyclerView revenueItems;

    ConnectionDetector mDetector;
    LinearLayoutManager linearLayoutManager;
    OrdersAdapter ordersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        ButterKnife.bind(this);

        setValues();
        CallService();

    }

    public void setValues(){
        mDetector = new ConnectionDetector(this);
        vI_aac_revenue_back.setOnClickListener(this);
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).RevenueList(WebServices.Foodey_Services,
                    WebServices.ApiType.revenuelist, Prefs.getUserId(this));
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.revenuelist) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                RevenueDetails revenueDetails = (RevenueDetails) response;
                if (revenueDetails != null){
                    ordersAdapter = new OrdersAdapter(this, revenueDetails);
                    linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    revenueItems.setLayoutManager(linearLayoutManager);
                    revenueItems.setAdapter(ordersAdapter);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vI_aac_revenue_back:
                finish();
                break;
        }
    }
}
