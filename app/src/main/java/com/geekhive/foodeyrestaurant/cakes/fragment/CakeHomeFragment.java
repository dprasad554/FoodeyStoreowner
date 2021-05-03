package com.geekhive.foodeyrestaurant.cakes.fragment;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.TextView;


import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.cakes.adapter.CakeHomeListAdapter;
import com.geekhive.foodeyrestaurant.cakes.beans.cakestoreneworder.StoreNewOrder;
import com.geekhive.foodeyrestaurant.cakes.utils.BaseActivityInterface;
import com.geekhive.foodeyrestaurant.cakes.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.cakes.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.cakes.utils.Prefs;
import com.geekhive.foodeyrestaurant.cakes.utils.SnackBar;
import com.geekhive.foodeyrestaurant.cakes.utils.WebServices;

public class CakeHomeFragment extends Fragment implements OnResponseListner {

    View v;
    BaseActivityInterface setToolBarTitle;
    RecyclerView homeRecyclerView;
    ConnectionDetector mDetector;
    Dialog paypopup;
    String message = "";

    public CakeHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.cake_home_fragment, container, false);
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

        setValues();
        CallService();
        pushNotification();
    }

    private void setValues() {

        mDetector = new ConnectionDetector(getActivity());
    }

    private void CallService() {
        String store_id = Prefs.getUserId(getActivity());
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).StoreNewOrder(WebServices.Foodey_Services,
                    WebServices.ApiType.storeneworder, store_id);
        }
    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSuccess) {
        if (apiType == WebServices.ApiType.storeneworder) {
            if (!isSuccess) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                StoreNewOrder storeNewOrder = (StoreNewOrder) response;
                if (!isSuccess || storeNewOrder == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (storeNewOrder != null) {
                        if (storeNewOrder.getOrder() != null) {
                            CakeHomeListAdapter homeListAdapter = new CakeHomeListAdapter(getActivity(), storeNewOrder);
                            homeRecyclerView.setAdapter(homeListAdapter);
                        } else {
                            SnackBar.makeText(getActivity(), "Record Not Found", SnackBar.LENGTH_SHORT).show();
                        }
                    } else {
                        SnackBar.makeText(getActivity(), "Record Not Found", SnackBar.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private void pushNotification() {

        try {

            Intent intent = getActivity().getIntent();
            message = intent.getExtras().getString("ms_cake");
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

    }


    private void InitializeOnlinePopup() {
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
    }
}

