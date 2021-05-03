package com.geekhive.foodeyrestaurant.cakes.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.cakes.beans.cakestorelogin.StoreLogin;
import com.geekhive.foodeyrestaurant.cakes.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.cakes.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.cakes.utils.Prefs;
import com.geekhive.foodeyrestaurant.cakes.utils.SnackBar;
import com.geekhive.foodeyrestaurant.cakes.utils.WebServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class CakeLoginActivity extends AppCompatActivity implements OnResponseListner {

    EditText vE_al_email;
    EditText vE_al_password;
    ConnectionDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cake_activity_login);

        vE_al_email = findViewById(R.id.vE_al_email);
        vE_al_password = findViewById(R.id.vE_al_password);
        mDetector = new ConnectionDetector(this);
    }

    public void doLogin(View view) {
        if (!(vE_al_email.getText().toString().equals("")) || !(vE_al_email.getText().toString().isEmpty())) {
            if (!(vE_al_password.getText().toString().equals("")) || !(vE_al_password.getText().toString().isEmpty())) {
                initializeFirebase();
            }else {
                vE_al_password.setError("Please enter password");
            }
        } else {
            vE_al_email.setError("Please enter email id or phone number");
        }
    }

    private void CallService(String token) {
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).Login(WebServices.Foodey_Services,
                    WebServices.ApiType.login, vE_al_email.getText().toString(), vE_al_password.getText().toString(), token);//, token
        }
    }

    private void initializeFirebase() {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("Home Activity", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        CallService(token);
                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("Home Activiy", msg);
                        //Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        // [END retrieve_current_token]
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {
        if (apiType == WebServices.ApiType.login) {
            if (!isSucces) {
                SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                StoreLogin storeLogin = (StoreLogin) response;
                if (!isSucces || storeLogin == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (storeLogin.getCakeStore() != null) {
                        Prefs.setCakeLogin(this, "true");
                        Prefs.setUserId(this, storeLogin.getCakeStore().getId());
                        Prefs.setUserName(this, storeLogin.getCakeStore().getName());
                        Prefs.setMobileNumber(this, storeLogin.getCakeStore().getMobile());
                        Prefs.setUserEmail(this, storeLogin.getCakeStore().getEmail());
//                        Prefs.setUserProfileUrl(this, storeLogin.getStore().getImage());
                        Intent intent = new Intent(this, CakeBottomNaviActivity.class);
                        startActivity(intent);
                        CakeLoginActivity.this.finish();
                    } else {
                        SnackBar.makeText(CakeLoginActivity.this, "Record Not Found", SnackBar.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }
}
