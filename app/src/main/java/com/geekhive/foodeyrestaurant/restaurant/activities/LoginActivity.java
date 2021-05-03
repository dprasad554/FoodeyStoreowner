package com.geekhive.foodeyrestaurant.restaurant.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.beans.login.LoginOut;
import com.geekhive.foodeyrestaurant.restaurant.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.restaurant.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.restaurant.utils.Prefs;
import com.geekhive.foodeyrestaurant.restaurant.utils.SnackBar;
import com.geekhive.foodeyrestaurant.restaurant.utils.WebServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class LoginActivity extends AppCompatActivity implements OnResponseListner {

    EditText vE_al_email;
    EditText vE_al_password;
    ConnectionDetector mDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        vE_al_email = findViewById(R.id.vE_al_email);
        vE_al_password = findViewById(R.id.vE_al_password);
        mDetector = new ConnectionDetector(this);
    }

    public void doLogin(View view) {
        if (!(vE_al_email.getText().toString().equals("")) || !(vE_al_email.getText().toString().isEmpty())) {
            if (!(vE_al_password.getText().toString().equals("")) || !(vE_al_password.getText().toString().isEmpty())) {
                initializeFirebase();
            } else {
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

                LoginOut loginOut = (LoginOut) response;
                if (!isSucces || loginOut == null) {
                    SnackBar.makeText(this, getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (loginOut.getResturant() != null) {
                        Prefs.setRestLogin(this, "true");
                        Prefs.setUserId(this, loginOut.getResturant().getId());
                        Prefs.setUserName(this, loginOut.getResturant().getName());
                        Prefs.setMobileNumber(this, loginOut.getResturant().getPhone());
                        Prefs.setUserEmail(this, loginOut.getResturant().getEmail());
                        Prefs.setUserProfileUrl(this, loginOut.getResturant().getImage());
                        if (loginOut.getResturant().getOpenStatus().equals("1")) {
                            Prefs.setOpenClose(this, "Open");
                        } else if (loginOut.getResturant().getOpenStatus().equals("0")) {
                            Prefs.setOpenClose(this, "Closed");
                        }
                        if (loginOut.getResturant().getTableBooking().equals("1")) {
                            Prefs.setOpenCloseTB(this, "Open");
                        } else if (loginOut.getResturant().getOpenStatus().equals("0")) {
                            Prefs.setOpenCloseTB(this, "Closed");
                        }


                        if (loginOut.getResturant().getTakeAway().equals("1")) {
                            Prefs.setOpenCloseTA(this, "Available");
                        } else if (loginOut.getResturant().getOpenStatus().equals("0")) {
                            Prefs.setOpenCloseTA(this, "Un-Available");
                        }

                        if (loginOut.getResturant().getSleepMode().equals("1")) {
                            Prefs.setOnOff(this, "On");
                        } else if (loginOut.getResturant().getOpenStatus().equals("0")) {
                            Prefs.setOnOff(this, "Off");
                        }

                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    } else {
                        SnackBar.makeText(LoginActivity.this, loginOut.getMessage(), SnackBar.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }

    public void doSignUp(View view) {

    }
}
