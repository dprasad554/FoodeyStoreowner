package com.geekhive.foodeyrestaurant.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;

public class Grocery_RevenueListAdapter extends RecyclerView.Adapter<Grocery_RevenueListAdapter.MyViewHolder> {


    Context context;

    public Grocery_RevenueListAdapter(Context context  ) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_revenue_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items

    }
    @Override
    public int getItemCount() {
        return 4;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView orderReceived, orderDelivered, orderCanceled, grandTotal, revenueHeading;
        public MyViewHolder(View itemView) {
            super(itemView);
            orderReceived = itemView.findViewById(R.id.orderReceived);
            orderDelivered = itemView.findViewById(R.id.orderDelivered);
            orderCanceled = itemView.findViewById(R.id.orderCanceled);
            grandTotal = itemView.findViewById(R.id.grandTotal);
            revenueHeading = itemView.findViewById(R.id.revenueHeading);
        }
    }
}