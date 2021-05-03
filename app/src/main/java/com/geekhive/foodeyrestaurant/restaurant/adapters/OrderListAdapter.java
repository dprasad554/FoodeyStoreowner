package com.geekhive.foodeyrestaurant.restaurant.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.activities.OrderDetailsActivity;
import com.geekhive.foodeyrestaurant.restaurant.beans.orderlist.OrderListOut;

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
                //callPhoneNumber();
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