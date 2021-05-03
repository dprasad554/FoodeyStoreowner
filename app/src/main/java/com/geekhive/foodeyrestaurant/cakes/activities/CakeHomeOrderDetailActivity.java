package com.geekhive.foodeyrestaurant.cakes.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.cakes.beans.cakecancelOrder.CancelOrder;
import com.geekhive.foodeyrestaurant.cakes.beans.cakeorderConfirm.OrderConfirmation;
import com.geekhive.foodeyrestaurant.cakes.beans.cakeorderdetailhistory.OrderDetailHistory;
import com.geekhive.foodeyrestaurant.cakes.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.cakes.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.cakes.utils.SnackBar;
import com.geekhive.foodeyrestaurant.cakes.utils.WebServices;

public class CakeHomeOrderDetailActivity extends AppCompatActivity implements OnResponseListner {

    Toolbar mActionBarToolbar;
    ConnectionDetector mDetector;
    int position_;
    TextView custName, custMob, orderId, vT_price;
    LinearLayout orderCancel;
    Button deliveryButton,ViewAllItem;
    String cart_id;
    OrderDetailHistory orderDetailHistory;
    CardView card_viewq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cake_activity_home_order_detail);

        mActionBarToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Order Details");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        custName = findViewById(R.id.custName);
        custMob = findViewById(R.id.custMob);
        orderId = findViewById(R.id.orderId);
        vT_price = findViewById(R.id.vT_price);
        orderCancel = findViewById(R.id.orderCancel);
        deliveryButton = findViewById(R.id.deliveryButton);
        position_ = getIntent().getIntExtra("position", 0);
        cart_id = getIntent().getStringExtra("cart_id");

        ViewAllItem = findViewById(R.id.viewAlItems);
        ViewAllItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CakeHomeOrderDetailActivity.this, CakeHomeViewOrderActivity.class);
                intent.putExtra("cart_id",cart_id);
                startActivity(intent);
            }
        });

        setValues();
        CallService();
    }

    private void setValues() {
        mDetector = new ConnectionDetector(this);
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).OrderDetailHistory(WebServices.Foodey_Services,
                    WebServices.ApiType.orderdetailhistory,cart_id);
        }
    }

    private void CallOrderService(String cart_id, String store_id) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).ConfirmOrder(WebServices.Foodey_Services,
                    WebServices.ApiType.confirmOrder, cart_id, store_id);
        }
    }

    private void CallCancelService(String cart_id, String store_id) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).CancelOrder(WebServices.Foodey_Services,
                    WebServices.ApiType.cancelOrder, cart_id, store_id);
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
                    if(orderDetailHistory.getOrder().getCakeCart().getStatus().equals("1")){
//                        card_viewq.setVisibility(View.GONE);
                        deliveryButton.setVisibility(View.VISIBLE);
                        orderCancel.setVisibility(View.GONE);
                    } else if(orderDetailHistory.getOrder().getCakeCart().getStatus().equals("2")){
                        deliveryButton.setVisibility(View.GONE);
                        orderCancel.setVisibility(View.GONE);
                    } else if(orderDetailHistory.getOrder().getCakeCart().getStatus().equals("3")){
                        deliveryButton.setVisibility(View.GONE);
                        orderCancel.setVisibility(View.GONE);
                    } else if(orderDetailHistory.getOrder().getCakeCart().getStatus().equals("4")){
                        deliveryButton.setVisibility(View.GONE);
                        orderCancel.setVisibility(View.GONE);
                    } else {
                        deliveryButton.setVisibility(View.GONE);
                        orderCancel.setVisibility(View.GONE);
                    }
                    final String name = orderDetailHistory.getOrder().getUser().getFirstname() + " " + orderDetailHistory.getOrder().getUser().getLastname();
                    custName.setText(name);
                    custMob.setText(orderDetailHistory.getOrder().getUser().getMobile());
                    orderId.setText(orderDetailHistory.getOrder().getCakeCart().getOrderId());
                    String price = "₹ " + orderDetailHistory.getOrder().getCakeCart().getGrandTotal();
                    vT_price.setText(price);
                    //Delivery boy details
                   /* String delivery = orderListOut.getOrderHistory().get(position_).getDeliveryBoy().getFirstname() + " " + orderListOut.getOrderHistory().get(position_).getDeliveryBoy().getLastname() + "\n\n" +
                            orderListOut.getOrderHistory().get(position_).getDeliveryBoy().getMobile();
                    deliveryBoy.setText(delivery);*/
                    //CallFoodListService(orderListOut.getOrderHistory().get(position_).getCart().getOrderId());
                  /*  MenuAdapter menuAdapter = new MenuAdapter();
                    orderItems.setAdapter(menuAdapter);*/
                }
            }
        } if (apiType == WebServices.ApiType.confirmOrder){
            OrderConfirmation orderConfirmation = (OrderConfirmation) response;
            if (!isSuccess || orderConfirmation == null) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                deliveryButton.setVisibility(View.GONE);
                orderCancel.setVisibility(View.VISIBLE);
            } else {
                deliveryButton.setVisibility(View.VISIBLE);
                orderCancel.setVisibility(View.GONE);
                startActivity(new Intent(this, CakeBottomNaviActivity.class));
                CakeHomeOrderDetailActivity.this.finish();
            }
        } if (apiType == WebServices.ApiType.cancelOrder){
            CancelOrder cancelOrder = (CancelOrder) response;
            if (!isSuccess || cancelOrder == null) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                deliveryButton.setVisibility(View.GONE);
                orderCancel.setVisibility(View.VISIBLE);
            } else {
                deliveryButton.setVisibility(View.VISIBLE);
                orderCancel.setVisibility(View.GONE);
                startActivity(new Intent(this, CakeBottomNaviActivity.class));
                CakeHomeOrderDetailActivity.this.finish();
            }
        }
    }

    public void doOrderConfirm(View view) {
        CallOrderService(orderDetailHistory.getOrder().getCakeCart().getId(), orderDetailHistory.getOrder().getCakeCart().getStoreId());
    }

    public void doCancelOrder(View view) {
        CallCancelService(orderDetailHistory.getOrder().getCakeCart().getId(), orderDetailHistory.getOrder().getCakeCart().getStoreId());
    }

    public void doDelivered(View view) {
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
