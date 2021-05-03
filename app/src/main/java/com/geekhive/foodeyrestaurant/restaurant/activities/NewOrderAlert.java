package com.geekhive.foodeyrestaurant.restaurant.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.beans.cancelOrder.CancelOrder;
import com.geekhive.foodeyrestaurant.restaurant.beans.neworder.Order;
import com.geekhive.foodeyrestaurant.restaurant.beans.neworderdetails.NewOrderDetails;
import com.geekhive.foodeyrestaurant.restaurant.beans.orderConfirm.OrderConfirmation;
import com.geekhive.foodeyrestaurant.restaurant.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.restaurant.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.restaurant.utils.Prefs;
import com.geekhive.foodeyrestaurant.restaurant.utils.SnackBar;
import com.geekhive.foodeyrestaurant.restaurant.utils.WebServices;

public class NewOrderAlert extends AppCompatActivity implements OnResponseListner {

    Toolbar mActionBarToolbar;
    ConnectionDetector mDetector;
    Order newOrderList;
    int position_;
    TextView storeName;
    String cart_id;
    Button slide_to_accept;
    private MediaPlayer player;
    RelativeLayout rr_main;
    ImageView iv_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_order_alert);
        Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getApplicationContext().getPackageName() + "/" + R.raw.howl);
        player = MediaPlayer.create(this,
                soundUri);
        //setting loop play to true
        //this will make the ringtone continuously playing
        player.setLooping(true);

        //staring the player
        player.start();
        /*mActionBarToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setTitle("Order Details");*/

        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
        storeName = findViewById(R.id.storeName);
        slide_to_accept = findViewById(R.id.slide_to_accept);
        iv_cancel = findViewById(R.id.iv_cancel);
        position_ = getIntent().getIntExtra("position", 0);
        cart_id = getIntent().getStringExtra("cart_id");
        mDetector = new ConnectionDetector(this);
        CallService();
        slide_to_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setLooping(false);

                //staring the player
                player.stop();
                CallOrderService(newOrderList.getOrder().get(position_).getCart().getId(), newOrderList.getOrder().get(position_).getCart().getResId());

            }
        });
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart_id != null &&  Prefs.getUserId(NewOrderAlert.this) != null){
                    CallCancelService(cart_id, Prefs.getUserId(NewOrderAlert.this));
                }

            }
        });



    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).NewOrderList(WebServices.Foodey_Services,
                    WebServices.ApiType.newOrderList, Prefs.getUserId(this));
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

        if (apiType == WebServices.ApiType.newOrderList) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {

                newOrderList = (Order) response;
                if (!isSucces || newOrderList == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (newOrderList != null){
                        if (newOrderList.getOrder() != null){
                            String name = newOrderList.getOrder().get(position_).getUser().getFirstname() + " " + newOrderList.getOrder().get(position_).getUser().getLastname()
                                    + "\n" + newOrderList.getOrder().get(position_).getUser().getMobile() + "\n \n" + newOrderList.getOrder().get(position_).getCart().getOrderId();
                            storeName.setText(name);
                            cart_id = newOrderList.getOrder().get(position_).getCart().getId();
                        } else {
                            SnackBar.makeText(this, newOrderList.getMessage(), SnackBar.LENGTH_SHORT).show();
                        }
                    } else {
                        SnackBar.makeText(this, "No record found", SnackBar.LENGTH_SHORT).show();
                    }


                }
            }
        } if (apiType == WebServices.ApiType.confirmOrder){
            OrderConfirmation orderConfirmation = (OrderConfirmation) response;
            if (!isSucces || orderConfirmation == null) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                /*SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                deliveryButton.setVisibility(View.GONE);
                orderCancel.setVisibility(View.VISIBLE);*/
            } else {
                /*deliveryButton.setVisibility(View.VISIBLE);
                orderCancel.setVisibility(View.GONE);*/
               /* startActivity(new Intent(this, MainActivity.class));
                NewOrderAlert.this.finish();*/
                Intent intenti = new Intent(this, MainActivity.class);
                intenti.putExtra("confirm", "confirm");
                startActivity(intenti);
            }
        } if (apiType == WebServices.ApiType.cancelOrder){
            CancelOrder cancelOrder = (CancelOrder) response;
            if (!isSucces || cancelOrder == null) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                /*deliveryButton.setVisibility(View.GONE);
                orderCancel.setVisibility(View.VISIBLE);*/
            } else {
                Intent intenti = new Intent(this, MainActivity.class);
                intenti.putExtra("confirm", "cancel");
                startActivity(intenti);
            }
        }

    }

    /*public void doOrderConfirm(View view) {
        CallOrderService(newOrderDetails.getOrder().getCart().getId(), newOrderDetails.getOrder().getCart().getResId());
    }

    public void doCancelOrder(View view) {
        CallCancelService(newOrderDetails.getOrder().getCart().getId(), newOrderDetails.getOrder().getCart().getResId());
    }*/

    /*public void doDelivered(View view) {

    }*/

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
