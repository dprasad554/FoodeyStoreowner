package com.geekhive.foodeyrestaurant.grocery.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.geekhive.foodeyrestaurant.R;

public class Grocery_NotificationActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grocery_activity_notification);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vI_aac_notification_back:
                finish();
                break;
        }
    }
}
