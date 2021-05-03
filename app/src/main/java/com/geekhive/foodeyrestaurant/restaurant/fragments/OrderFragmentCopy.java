package com.geekhive.foodeyrestaurant.restaurant.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.adapters.OrderListAdapter;
import com.geekhive.foodeyrestaurant.restaurant.beans.orderlist.OrderListOut;
import com.geekhive.foodeyrestaurant.restaurant.utils.BaseActivityInterface;
import com.geekhive.foodeyrestaurant.restaurant.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.restaurant.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.restaurant.utils.Prefs;
import com.geekhive.foodeyrestaurant.restaurant.utils.SnackBar;
import com.geekhive.foodeyrestaurant.restaurant.utils.WebServices;

public class OrderFragmentCopy extends Fragment implements OnResponseListner {

    View v;
    BaseActivityInterface setToolBarTitle;
    RecyclerView orderRecyclerView;
    ConnectionDetector mDetector;

    public OrderFragmentCopy() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.order_fragment, container, false);
        initialiseView(v, this.getActivity());

        setHasOptionsMenu(false);

        setToolBarTitle = (BaseActivityInterface) getActivity();
        setToolBarTitle.setToolBarTitle("Order History");


        return v;

    }

    void initialiseView(View v, final Context ctx) {
        mDetector = new ConnectionDetector(getActivity());
        orderRecyclerView = v.findViewById(R.id.orderRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        orderRecyclerView.setLayoutManager(linearLayoutManager);

        CallService();

    }

    private void CallService() {
        String id = Prefs.getUserId(getActivity());
        if (this.mDetector.isConnectingToInternet()) {
            new WebServices(this).OrderList(WebServices.Foodey_Services,
                    WebServices.ApiType.restaurantList, id );
        }

    }

    @Override
    public void onResponse(Object response, WebServices.ApiType apiType, boolean isSucces) {

        if (apiType == WebServices.ApiType.restaurantList) {
            if (!isSucces) {
                SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
            } else {

                OrderListOut orderListOut = (OrderListOut) response;
                if (!isSucces || orderListOut == null) {
                    SnackBar.makeText(getActivity(), getResources().getString(R.string.something_wrong), SnackBar.LENGTH_SHORT).show();
                } else {
                    if (orderListOut != null){
                        if (orderListOut.getOrderHistory() != null){
                            OrderListAdapter orderListAdapter = new OrderListAdapter(getActivity(), orderListOut);
                            orderRecyclerView.setAdapter(orderListAdapter);
                        } else {
                            SnackBar.makeText(getActivity(), "No record found", SnackBar.LENGTH_SHORT).show();
                        }
                    } else {
                        SnackBar.makeText(getActivity(), "No record found", SnackBar.LENGTH_SHORT).show();
                    }

                }
            }
        }
    }
}
