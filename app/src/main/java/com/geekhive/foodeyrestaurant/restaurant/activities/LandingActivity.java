package com.geekhive.foodeyrestaurant.restaurant.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.cakes.activities.CakeBottomNaviActivity;
import com.geekhive.foodeyrestaurant.grocery.activities.Grocery_BottomNaviActivity;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
    }

    public void doRestOwnerApp(View view) {
        startActivity(new Intent(LandingActivity.this, MainActivity.class));
    }

    public void doGroceryOwner(View view) {
        startActivity(new Intent(LandingActivity.this, Grocery_BottomNaviActivity.class));
    }

    public void doCakeOwner(View view) {
        startActivity(new Intent(LandingActivity.this, CakeBottomNaviActivity.class));
    }
}
