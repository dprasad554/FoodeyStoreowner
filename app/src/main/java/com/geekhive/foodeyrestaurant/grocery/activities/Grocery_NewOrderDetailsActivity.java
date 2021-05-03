package com.geekhive.foodeyrestaurant.grocery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.grocery.beans.grocerycancelOrder.CancelOrder;
import com.geekhive.foodeyrestaurant.grocery.beans.groceryorderConfirm.OrderConfirmation;
import com.geekhive.foodeyrestaurant.grocery.beans.groceryorderdetailhistory.OrderDetailHistory;
import com.geekhive.foodeyrestaurant.grocery.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.grocery.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.grocery.utils.SnackBar;
import com.geekhive.foodeyrestaurant.grocery.utils.WebServices;

public class Grocery_NewOrderDetailsActivity extends AppCompatActivity implements OnResponseListner {

    Toolbar mActionBarToolbar;
    ConnectionDetector mDetector;
    int position_;
    TextView custName, custMob, orderId, vT_price;
    LinearLayout orderCancel;
    Button deliveryButton,ViewAllItem;
    String cart_id;
    OrderDetailHistory orderDetailHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_activity_new_order_details);

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
                Intent intent = new Intent(Grocery_NewOrderDetailsActivity.this, Grocery_ViewOrderDetailsActivity.class);
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
                    if(!orderDetailHistory.getOrder().getGCart().getStatus().equals("6")){
                        if (orderDetailHistory.getOrder().getGCart().getStatus().equals("1")){
                            deliveryButton.setVisibility(View.VISIBLE);
                            orderCancel.setVisibility(View.GONE);
                        } else {
                            deliveryButton.setVisibility(View.GONE);
                            orderCancel.setVisibility(View.VISIBLE);
                        }
                    } else {
                        deliveryButton.setVisibility(View.GONE);
                        orderCancel.setVisibility(View.VISIBLE);
                    }
                    final String name = orderDetailHistory.getOrder().getUser().getFirstname() + " " + orderDetailHistory.getOrder().getUser().getLastname();
                    custName.setText(name);
                    custMob.setText(orderDetailHistory.getOrder().getUser().getMobile());
                    orderId.setText(orderDetailHistory.getOrder().getGCart().getOrderId());
                    String price = "₹ " + orderDetailHistory.getOrder().getGCart().getGrandTotal();
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
                startActivity(new Intent(this, Grocery_BottomNaviActivity.class));
                Grocery_NewOrderDetailsActivity.this.finish();
            }
        } //For Cancel Order
        if (apiType == WebServices.ApiType.cancelOrder){
            CancelOrder cancelOrder = (CancelOrder) response;
            if (!isSuccess || cancelOrder == null) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                deliveryButton.setVisibility(View.GONE);
                orderCancel.setVisibility(View.VISIBLE);
            } else {
                deliveryButton.setVisibility(View.VISIBLE);
                orderCancel.setVisibility(View.GONE);
                startActivity(new Intent(this, Grocery_BottomNaviActivity.class));
                Grocery_NewOrderDetailsActivity.this.finish();
            }
        }
    }

    public void doOrderConfirm(View view) {
       CallOrderService(orderDetailHistory.getOrder().getGCart().getId(), orderDetailHistory.getOrder().getGCart().getStoreId());
    }

    public void doCancelOrder(View view) {
        CallCancelService(orderDetailHistory.getOrder().getGCart().getId(), orderDetailHistory.getOrder().getGCart().getStoreId());
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

    public void doDelivered(View view) {
    }
}
