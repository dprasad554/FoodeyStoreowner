package com.geekhive.foodeyrestaurant.restaurant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.beans.cancelOrder.CancelOrder;
import com.geekhive.foodeyrestaurant.restaurant.beans.neworderdetails.NewOrderDetails;
import com.geekhive.foodeyrestaurant.restaurant.beans.orderConfirm.OrderConfirmation;
import com.geekhive.foodeyrestaurant.restaurant.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.restaurant.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.restaurant.utils.SnackBar;
import com.geekhive.foodeyrestaurant.restaurant.utils.WebServices;

public class NewOrderDetailsActivity extends AppCompatActivity implements OnResponseListner {

    Toolbar mActionBarToolbar;
    ConnectionDetector mDetector;
    NewOrderDetails newOrderDetails;
    String price_;
    String foodId;
    RecyclerView orderItems;
    int position_;
    TextView custName, custMob, orderId, vT_price;
    LinearLayout orderCancel;
    Button deliveryButton;
    String cart_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

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
        mDetector = new ConnectionDetector(this);
        orderItems = findViewById(R.id.orderItems);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        orderItems.setLayoutManager(linearLayoutManager);

        CallService();

    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).NewOrderDetails(WebServices.Foodey_Services,
                    WebServices.ApiType.neworderdetails, cart_id);
        }

    }

    private void CallOrderService(String cart_id, String res_id) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).ConfirmOrder(WebServices.Foodey_Services,
                    WebServices.ApiType.confirmOrder, cart_id, res_id);
        }

    }

    private void CallCancelService(String cart_id, String res_id) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).CancelOrder(WebServices.Foodey_Services,
                    WebServices.ApiType.cancelOrder, cart_id, res_id);
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.neworderdetails) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {

                newOrderDetails = (NewOrderDetails) response;
                if (!isSucces || newOrderDetails == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if(newOrderDetails.getOrder().getCart().getOrderStatus().equals("Confirm")){
                        deliveryButton.setVisibility(View.VISIBLE);
                        orderCancel.setVisibility(View.GONE);
                    } else {
                        deliveryButton.setVisibility(View.GONE);
                        orderCancel.setVisibility(View.VISIBLE);
                    }
                    final String name = newOrderDetails.getOrder().getUser().getFirstname() + " " + newOrderDetails.getOrder().getUser().getLastname();
                    custName.setText(name);
                    custMob.setText(newOrderDetails.getOrder().getUser().getMobile());
                    orderId.setText(newOrderDetails.getOrder().getCart().getOrderId());
                    String price = "₹ " + newOrderDetails.getOrder().getCart().getTotel();
                    vT_price.setText(price);
                    MenuAdapter menuAdapter = new MenuAdapter();
                    orderItems.setAdapter(menuAdapter);
                }
            }
        } if (apiType == WebServices.ApiType.confirmOrder){
            OrderConfirmation orderConfirmation = (OrderConfirmation) response;
            if (!isSucces || orderConfirmation == null) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                deliveryButton.setVisibility(View.GONE);
                orderCancel.setVisibility(View.VISIBLE);
            } else {
                deliveryButton.setVisibility(View.VISIBLE);
                orderCancel.setVisibility(View.GONE);
                startActivity(new Intent(this, MainActivity.class));
                NewOrderDetailsActivity.this.finish();
            }
        } if (apiType == WebServices.ApiType.cancelOrder){
            CancelOrder cancelOrder = (CancelOrder) response;
            if (!isSucces || cancelOrder == null) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                deliveryButton.setVisibility(View.GONE);
                orderCancel.setVisibility(View.VISIBLE);
            } else {
                deliveryButton.setVisibility(View.VISIBLE);
                orderCancel.setVisibility(View.GONE);
                startActivity(new Intent(this, MainActivity.class));
                NewOrderDetailsActivity.this.finish();
            }
        }

    }

    public void doOrderConfirm(View view) {
        CallOrderService(newOrderDetails.getOrder().getCart().getId(), newOrderDetails.getOrder().getCart().getResId());
    }

    public void doCancelOrder(View view) {
        CallCancelService(newOrderDetails.getOrder().getCart().getId(), newOrderDetails.getOrder().getCart().getResId());
    }

    public void doDelivered(View view) {

    }

    public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_menu_details, parent, false));
        }

        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            String items = newOrderDetails.getOrder().getCartDetail().get(position).getFood().getName() + " * " + newOrderDetails.getOrder().getCartDetail().get(position).getQuantity();
            holder.vT_admd_text.setText(items);
            price_ = newOrderDetails.getOrder().getCartDetail().get(position).getFood().getPrice();
            String price = "₹ " + newOrderDetails.getOrder().getCartDetail().get(position).getFood().getPrice();
            holder.vT_price.setText(price);


        }

        public int getItemCount() {
            return newOrderDetails.getOrder().getCartDetail().size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            LinearLayout vL_admd_main, vL_admd_hide;
            ImageView vI_admd_image;
            TextView vT_admd_text, vT_price;
            ImageView qtyM, qtyA;
            TextView qty;
            Button buttonAdd;

            public MyViewHolder(View view) {
                super(view);
                this.vL_admd_main = (LinearLayout) view.findViewById(R.id.vL_admd_main);
                this.vL_admd_hide = (LinearLayout) view.findViewById(R.id.vL_admd_hide);
                this.vT_admd_text = (TextView) view.findViewById(R.id.vT_admd_text);
                vT_price = view.findViewById(R.id.vT_price);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
