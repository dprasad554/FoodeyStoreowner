package com.geekhive.foodeyrestaurant.restaurant.notifications;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.adapters.NotificationListAdapter;
import com.geekhive.foodeyrestaurant.restaurant.beans.notification.Notifications;
import com.geekhive.foodeyrestaurant.restaurant.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.restaurant.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.restaurant.utils.Prefs;
import com.geekhive.foodeyrestaurant.restaurant.utils.SnackBar;
import com.geekhive.foodeyrestaurant.restaurant.utils.WebServices;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationActivity extends AppCompatActivity implements View.OnClickListener, OnResponseListner {
    @BindView(R.id.vI_aac_notification_back)
    ImageView vI_aac_notification_back;
    @BindView(R.id.notificationItems)
    RecyclerView notificationItems;

    ConnectionDetector mDetector;
    LinearLayoutManager linearLayoutManager;
    NotificationListAdapter notificationListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);

        setValues();
        CallService();

    }

    public void setValues(){
        mDetector = new ConnectionDetector(this);
        vI_aac_notification_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vI_aac_notification_back:
                finish();
                break;
        }
    }

    private void CallService() {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).NotificationList(WebServices.Foodey_Services,
                    WebServices.ApiType.notificationlist, Prefs.getUserId(this));
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.notificationlist) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                Notifications notifications = (Notifications) response;
                if (notifications != null){
                    notificationListAdapter = new NotificationListAdapter(this, notifications);
                    linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                    notificationItems.setLayoutManager(linearLayoutManager);
                    notificationItems.setAdapter(notificationListAdapter);
                }
            }
        }
    }

}
