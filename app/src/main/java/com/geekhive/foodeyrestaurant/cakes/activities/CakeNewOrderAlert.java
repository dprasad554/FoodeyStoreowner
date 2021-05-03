package com.geekhive.foodeyrestaurant.cakes.activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.cakes.adapter.CakeHomeListAdapter;
import com.geekhive.foodeyrestaurant.cakes.beans.cakecancelOrder.CancelOrder;
import com.geekhive.foodeyrestaurant.cakes.beans.cakeorderConfirm.OrderConfirmation;
import com.geekhive.foodeyrestaurant.cakes.beans.cakestoreneworder.StoreNewOrder;
import com.geekhive.foodeyrestaurant.cakes.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.cakes.utils.Prefs;
import com.geekhive.foodeyrestaurant.cakes.utils.SnackBar;
import com.geekhive.foodeyrestaurant.cakes.utils.WebServices;
import com.geekhive.foodeyrestaurant.restaurant.activities.MainActivity;
import com.geekhive.foodeyrestaurant.restaurant.activities.NewOrderAlert;
import com.geekhive.foodeyrestaurant.restaurant.utils.ConnectionDetector;

public class CakeNewOrderAlert extends AppCompatActivity implements OnResponseListner {

    Toolbar mActionBarToolbar;
    ConnectionDetector mDetector;
    StoreNewOrder storeNewOrder;
    int position_ = 0;
    TextView storeName;
    String cart_id;
    Button slide_to_accept;
    private MediaPlayer player;
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
        position_ = getIntent().getIntExtra("position", 0);
        cart_id = getIntent().getStringExtra("cart_id");
        iv_cancel = findViewById(R.id.iv_cancel);
        mDetector = new ConnectionDetector(this);

        slide_to_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.setLooping(false);

                //staring the player
                player.stop();
                CallOrderService(storeNewOrder.getOrder().get(position_).getCakeCart().getId(), storeNewOrder.getOrder().get(position_).getCakeCart().getStoreId());
            }
        });
        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart_id != null &&  com.geekhive.foodeyrestaurant.restaurant.utils.Prefs.getUserId(CakeNewOrderAlert.this) != null){
                    CallCancelService(cart_id, com.geekhive.foodeyrestaurant.restaurant.utils.Prefs.getUserId(CakeNewOrderAlert.this));
                }

            }
        });
        CallService();

    }

    private void CallService() {
        String store_id = Prefs.getUserId(this);
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).StoreNewOrder(WebServices.Foodey_Services,
                    WebServices.ApiType.storeneworder, store_id);
        }
    }

    private void CallCancelService(String cart_id, String store_id) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).CancelOrder(WebServices.Foodey_Services,
                    WebServices.ApiType.cancelOrder, cart_id, store_id);
        }
    }

    private void CallOrderService(String cart_id, String store_id) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).ConfirmOrder(WebServices.Foodey_Services,
                    WebServices.ApiType.confirmOrder, cart_id, store_id);
        }
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSuccess) {
        if (apiType == WebServices.ApiType.storeneworder) {
            if (!isSuccess) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                storeNewOrder = (StoreNewOrder) response;
                if (!isSuccess || storeNewOrder == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (storeNewOrder != null) {
                        if (storeNewOrder.getOrder() != null) {
                            String name = storeNewOrder.getOrder().get(position_).getUser().getFirstname() + " " + storeNewOrder.getOrder().get(position_).getUser().getLastname()
                                    + "\n" + storeNewOrder.getOrder().get(position_).getUser().getMobile() + "\n \n" + storeNewOrder.getOrder().get(position_).getCakeCart().getOrderId();
                            storeName.setText(name);
                        } else {
                            SnackBar.makeText(this, "Record Not Found", SnackBar.LENGTH_SHORT).show();
                        }
                    } else {
                        SnackBar.makeText(this, "Record Not Found", SnackBar.LENGTH_SHORT).show();
                    }
                }
            }
        } if (apiType == WebServices.ApiType.confirmOrder){
            OrderConfirmation orderConfirmation = (OrderConfirmation) response;
            if (!isSuccess || orderConfirmation == null) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                Intent intenti = new Intent(this, CakeBottomNaviActivity.class);
                intenti.putExtra("confirm", "confirm");
                startActivity(intenti);
                finish();
            }
        }if (apiType == WebServices.ApiType.cancelOrder){
            CancelOrder cancelOrder = (CancelOrder) response;
            if (!isSuccess || cancelOrder == null) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                Intent intenti = new Intent(this, CakeBottomNaviActivity.class);
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
