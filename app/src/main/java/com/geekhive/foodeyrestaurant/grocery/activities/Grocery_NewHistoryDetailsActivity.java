package com.geekhive.foodeyrestaurant.grocery.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;

public class Grocery_NewHistoryDetailsActivity extends AppCompatActivity  {

    Toolbar mActionBarToolbar;
    String price_;
    String foodId;
    RecyclerView orderItems;
    int position_;
    TextView custName, custMob, orderId, vT_price;
    LinearLayout orderCancel;
    Button deliveryButton,ViewAllItem;
    String cart_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_activity_order_history_detail);

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
                Intent intent = new Intent(Grocery_NewHistoryDetailsActivity.this, Grocery_ViewOrderDetailsActivity.class);
                startActivity(intent);
            }
        });

    }

    public void doOrderConfirm(View view) {
//        CallOrderService(newOrderDetails.getOrder().getCart().getId(), newOrderDetails.getOrder().getCart().getResId());
    }

    public void doCancelOrder(View view) {
//        CallCancelService(newOrderDetails.getOrder().getCart().getId(), newOrderDetails.getOrder().getCart().getResId());
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
