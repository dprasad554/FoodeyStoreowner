package com.geekhive.foodeyrestaurant.restaurant.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.adapters.HomeListAdapter;
import com.geekhive.foodeyrestaurant.restaurant.beans.neworder.Order;
import com.geekhive.foodeyrestaurant.restaurant.utils.BaseActivityInterface;
import com.geekhive.foodeyrestaurant.restaurant.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.restaurant.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.restaurant.utils.Prefs;
import com.geekhive.foodeyrestaurant.restaurant.utils.SnackBar;
import com.geekhive.foodeyrestaurant.restaurant.utils.WebServices;

public class HomeFragment extends Fragment implements OnResponseListner {

    View v;
    BaseActivityInterface setToolBarTitle;
    RecyclerView homeRecyclerView;
    ConnectionDetector mDetector;
    String message = "";
    Dialog paypopup;
    //ConnectionDetector mDetector;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.home_fragment, container, false);
        initialiseView(v, this.getActivity());

        setHasOptionsMenu(false);

        setToolBarTitle = (BaseActivityInterface) getActivity();
        setToolBarTitle.setToolBarTitle("Home");


        return v;

    }

    void initialiseView(View v, final Context ctx) {
        mDetector = new ConnectionDetector(getActivity());
        homeRecyclerView = v.findViewById(R.id.homeRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        homeRecyclerView.setLayoutManager(linearLayoutManager);

        CallService();
        //pushNotification();


    }

    private void CallService() {
        String id = Prefs.getUserId(getActivity());
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).NewOrderList(WebServices.Foodey_Services,
                    WebServices.ApiType.newOrderList, Prefs.getUserId(getActivity()));
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.newOrderList) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {

                Order newOrderList = (Order) response;
                if (!isSucces || newOrderList == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (newOrderList != null){
                        if (newOrderList.getOrder() != null){
                            HomeListAdapter homeListAdapter = new HomeListAdapter(getActivity(), newOrderList);
                            homeRecyclerView.setAdapter(homeListAdapter);
                        } else {
                            SnackBar.makeText(getActivity(), newOrderList.getMessage(), SnackBar.LENGTH_SHORT).show();
                        }
                    } else {
                        SnackBar.makeText(getActivity(), "No record found", SnackBar.LENGTH_SHORT).show();
                    }


                }
            }
        }

    }


    /*private void pushNotification() {

        try {

            Intent intent = getActivity().getIntent();
            message = intent.getExtras().getString("ms");
            if (!message.equals("")) {

                InitializeOnlinePopup();
                initializeOnlinePopup(message);

            } else {

                Log.e("Message", "No Message To Display");

            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }*/


    /*private void InitializeOnlinePopup() {
        paypopup = new Dialog(getActivity());
        paypopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        paypopup.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        paypopup.setContentView(R.layout.order_popupx);
        paypopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        paypopup.setCancelable(true);
        paypopup.setCanceledOnTouchOutside(true);
    }

    private void initializeOnlinePopup(String msg) {
        paypopup.setContentView(R.layout.order_popupx);
        paypopup.setCancelable(true);
        paypopup.setCanceledOnTouchOutside(true);
        paypopup.show();

        final TextView v_online = paypopup.findViewById(R.id.v_online);



//        final TextView view_cart = (TextView) paypopup.findViewById(R.id.view_cart);
//        final ImageView view_cart_img =  paypopup.findViewById(R.id.view_cart_img);


        int width = getResources().getDisplayMetrics().widthPixels - 100;
        int height = getResources().getDisplayMetrics().heightPixels - 250;
        paypopup.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        paypopup.getWindow().setGravity(Gravity.CENTER);


        paypopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        paypopup.setCancelable(true);
        paypopup.setCanceledOnTouchOutside(true);
        paypopup.show();
    }*/

}
