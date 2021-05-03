package com.geekhive.foodeyrestaurant.restaurant.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.activities.OrderDetailsActivity;
import com.geekhive.foodeyrestaurant.restaurant.adapters.OrderListAdapter;
import com.geekhive.foodeyrestaurant.restaurant.beans.orderlist.OrderListOut;
import com.geekhive.foodeyrestaurant.restaurant.utils.BaseActivityInterface;
import com.geekhive.foodeyrestaurant.restaurant.utils.ConnectionDetector;
import com.geekhive.foodeyrestaurant.restaurant.utils.OnResponseListner;
import com.geekhive.foodeyrestaurant.restaurant.utils.Prefs;
import com.geekhive.foodeyrestaurant.restaurant.utils.SnackBar;
import com.geekhive.foodeyrestaurant.restaurant.utils.WebServices;

public class OrderFragment extends Fragment implements OnResponseListner {

    View v;
    BaseActivityInterface setToolBarTitle;
    RecyclerView orderRecyclerView;
    ConnectionDetector mDetector;
    String custNo = "";
    private final int CALL_REQUEST = 100;

    public OrderFragment() {
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

    public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {
        public Typeface Montserrat_Regular;
        public Typeface Montserrat_SemiBold;
        Context context;
        OrderListOut orderListOut;
        //ProductCategoryList productCategoryList;
        private final int CALL_REQUEST = 100;

        public OrderListAdapter(Context context, OrderListOut orderListOut) {
            this.context = context;
            this.orderListOut = orderListOut;
            //this.productCategoryList = productCategoryList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // infalte the item Layout
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list_item, parent, false);
            // set the view's size, margins, paddings and layout parameters
            MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
            return vh;
        }


        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            // set the data in items

            final String name = orderListOut.getOrderHistory().get(position).getUser().getFirstname() + " " + orderListOut.getOrderHistory().get(position).getUser().getLastname();
            holder.customerName.setText(name);
            holder.customerMob.setText(orderListOut.getOrderHistory().get(position).getUser().getMobile());
            holder.orderId.setText(orderListOut.getOrderHistory().get(position).getCart().getOrderId());
            holder.orderTime.setText(orderListOut.getOrderHistory().get(position).getCart().getDate());
            if (orderListOut.getOrderHistory().get(position).getCart().getStatus().equals("3")) {
                holder.viewOrder.setText("Delivery Boy Assigned");
            } else if (orderListOut.getOrderHistory().get(position).getCart().getStatus().equals("2")) {
                holder.viewOrder.setText("Canceled");
            } else if (orderListOut.getOrderHistory().get(position).getCart().getStatus().equals("4")) {
                holder.viewOrder.setText("Delivered");
            }
            holder.viewOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, OrderDetailsActivity.class);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });

            holder.productDetailsCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, OrderDetailsActivity.class);
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
            holder.tv_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    custNo = orderListOut.getOrderHistory().get(position).getUser().getMobile();
                    callPhoneNumber();
                }
            });

        }
        @Override
        public int getItemCount() {
            return orderListOut.getOrderHistory().size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            // init the item view's
            TextView customerName, customerMob, orderId, orderTime,tv_call;
            Button viewOrder;
            CardView productDetailsCard;

            public MyViewHolder(View itemView) {
                super(itemView);
                viewOrder = itemView.findViewById(R.id.viewOrder);
                customerName = itemView.findViewById(R.id.customerName);
                customerMob = itemView.findViewById(R.id.customerMob);
                orderId = itemView.findViewById(R.id.orderId);
                productDetailsCard = itemView.findViewById(R.id.productDetailsCard);
                orderTime = itemView.findViewById(R.id.orderTime);
                tv_call = itemView.findViewById(R.id.tv_call);
            }
        }
    }
    public void callPhoneNumber() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, CALL_REQUEST);

                    return;
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + custNo));
                    startActivity(callIntent);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
