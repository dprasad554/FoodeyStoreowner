package com.geekhive.foodeyrestaurant.grocery.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.grocery.adapter.Grocery_OrderHistoryListAdapter;
import com.geekhive.foodeyrestaurant.grocery.beans.grocerystoreorderhistory.OrderHistory;
import com.geekhive.foodeyrestaurant.grocery.utils.BaseActivityInterface;
import com.geekhive.foodeyrestaurant.grocery.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.grocery.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.grocery.utils.PrefsGrocery;
import com.geekhive.foodeyrestaurant.grocery.utils.SnackBar;
import com.geekhive.foodeyrestaurant.grocery.utils.WebServices;

import java.util.Collections;

public class GroceryOrderFragment extends Fragment implements OnResponseListner {

    View v;
    BaseActivityInterface setToolBarTitle;
    RecyclerView orderHistoryRecyclerView;
    ConnectionDetector mDetector;

    public GroceryOrderFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.grocery_order_fragment, container, false);
        initialiseView(v, this.getActivity());

        setHasOptionsMenu(false);

//        setToolBarTitle = (BaseActivityInterface) getActivity();
//        setToolBarTitle.setToolBarTitle("Order History");

        return v;
    }

    void initialiseView(View v, final Context ctx) {
        mDetector = new ConnectionDetector(getActivity());
        orderHistoryRecyclerView = v.findViewById(R.id.orderRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        orderHistoryRecyclerView.setLayoutManager(linearLayoutManager);

        setValues();
        CallService();
    }

    private void setValues() {

        mDetector = new ConnectionDetector(getActivity());
    }

    private void CallService() {
        String id = PrefsGrocery.getUserIdGrocery(getActivity());
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).StoreOrderHistory(WebServices.Foodey_Services,
                    WebServices.ApiType.storeorderhistory, id);
        }
    }



    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSuccess) {
        if (apiType == WebServices.ApiType.storeorderhistory) {
            if (!isSuccess) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {
                OrderHistory orderHistory = (OrderHistory) response;
                if (!isSuccess || orderHistory == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (orderHistory != null){
                        if (orderHistory.getOrderDetail() != null){
                            Collections.reverse(orderHistory.getOrderDetail());
                            Grocery_OrderHistoryListAdapter orderListAdapter = new Grocery_OrderHistoryListAdapter(getActivity(),orderHistory.getOrderDetail());
                            orderHistoryRecyclerView.setAdapter(orderListAdapter);
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
}
