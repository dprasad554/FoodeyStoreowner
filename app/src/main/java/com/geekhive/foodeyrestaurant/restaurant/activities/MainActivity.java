package com.geekhive.foodeyrestaurant.restaurant.activities;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.fragments.BookingFragment;
import com.geekhive.foodeyrestaurant.restaurant.fragments.HomeFragment;
import com.geekhive.foodeyrestaurant.restaurant.fragments.OrderFragment;
import com.geekhive.foodeyrestaurant.restaurant.fragments.ProfileFragment;
import com.geekhive.foodeyrestaurant.restaurant.utils.BaseActivityInterface;
import com.geekhive.foodeyrestaurant.restaurant.utils.Prefs;

public class MainActivity extends AppCompatActivity implements BaseActivityInterface {

    private Fragment viewFragment;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    Toolbar toolbar;
    NotificationManager notificationManager;
    String confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Prefs.getRestLogin(this).equals("")){
            startActivity(new Intent(this, LoginActivity.class));
            MainActivity.this.finish();
        }
        confirm = getIntent().getStringExtra("confirm");
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView, new OrderFragment()).commit();

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                viewFragment = new HomeFragment();
                                mFragmentTransaction = mFragmentManager.beginTransaction();
                                mFragmentTransaction.replace(R.id.containerView,viewFragment).commit();
                                break;
                            case R.id.action_orders:
                                viewFragment = new OrderFragment();
                                mFragmentTransaction = mFragmentManager.beginTransaction();
                                mFragmentTransaction.replace(R.id.containerView,viewFragment).commit();
                                break;
                            case R.id.action_booking:
                                viewFragment = new BookingFragment();
                                mFragmentTransaction = mFragmentManager.beginTransaction();
                                mFragmentTransaction.replace(R.id.containerView,viewFragment).commit();
                                break;
                            case R.id.action_profile:
                                viewFragment = new ProfileFragment();
                                mFragmentTransaction = mFragmentManager.beginTransaction();
                                mFragmentTransaction.replace(R.id.containerView,viewFragment).commit();
                                break;
                        }
                        return false;
                    }
                });
    }

    @Override
    public void setToolBarTitle(String name) {
        toolbar.setTitle(name);
    }

    public BroadcastReceiver myReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            registerReceiver(myReceiver, new IntentFilter("FBR-IMAGE"));
            String action = intent.getAction();
            //endProgress();
            if (notificationManager != null)
                notificationManager.cancelAll(); //closes notification
            removeStickyBroadcast(intent);
            if (intent.getStringExtra("topic").equals("Resturant")){
                pushNotificationFood(intent);
            } /*else if (intent.getStringExtra("topic").equals("Grocery")){
                pushNotificationGrocery(intent);
            } else if (intent.getStringExtra("topic").equals("Cake")){
                pushNotificationCake(intent);
            }*/

            //changeUi(action);
        }
    };

    private void pushNotificationFood(Intent intent) {

        try {

            String message = intent.getExtras().getString("ms");
            if (!message.equals("") ) {
                if(confirm == null){
                    Intent intenti = new Intent(this, NewOrderAlert.class);
                    intenti.putExtra("position", 0);
                    startActivity(intenti);
                    finish();
/*
                    if(confirm.equals("cancel")){

                    }
*/

                }

            } else {

                Log.e("Message", "No Message To Display");

            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //registerReceiver(broadcastReceiver, new IntentFilter(GoogleService.str_receiver));

        try {
            registerReceiver(myReceiver, new IntentFilter("FBR-IMAGE"));
        } catch (Exception e) {
            // already registered
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        //unregisterReceiver(broadcastReceiver);

        try {
            unregisterReceiver(myReceiver);
        } catch (Exception e) {
            // already unregistered
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//        if(passHandler!=null)
//            passHandler.removeCallbacksAndMessages(null);
        //stop location updates when Activity is no longer active
//        if (mGoogleApiClient != null) {
//            if(mGoogleApiClient.isConnected())
//                LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
//        }
    }
}
