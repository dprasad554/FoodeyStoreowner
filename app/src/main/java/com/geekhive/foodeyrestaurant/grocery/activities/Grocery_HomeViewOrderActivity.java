package com.geekhive.foodeyrestaurant.grocery.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.grocery.adapter.Grocery_HomeOrderDetailAdapter;
import com.geekhive.foodeyrestaurant.grocery.beans.groceryorderdetailhistory.OrderDetailHistory;
import com.geekhive.foodeyrestaurant.grocery.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.grocery.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.grocery.utils.SnackBar;
import com.geekhive.foodeyrestaurant.grocery.utils.WebServices;

public class Grocery_HomeViewOrderActivity extends AppCompatActivity implements OnResponseListner {

    Toolbar toolbar;
    RecyclerView orderDetailRecyclerView;
    ConnectionDetector mDetector;
    OrderDetailHistory orderDetailHistory;
    String cart_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_activity_home_view_order);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home View Orders");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        orderDetailRecyclerView = findViewById(R.id.homevieworder_recyclerView);
        cart_id = getIntent().getStringExtra("cart_id");

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
        if (apiType == WebServices.ApiType.orderdetailhistory) {
            if (!isSuccess) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                orderDetailHistory = (OrderDetailHistory) response;
                if (!isSuccess || orderDetailHistory == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    orderDetailRecyclerView.setLayoutManager(linearLayoutManager);
                    Grocery_HomeOrderDetailAdapter homeAdapter = new Grocery_HomeOrderDetailAdapter(this,orderDetailHistory);
                    orderDetailRecyclerView.setAdapter(homeAdapter);
                }
            }
        }
    }
}
