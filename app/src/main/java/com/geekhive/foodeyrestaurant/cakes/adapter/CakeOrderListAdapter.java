package com.geekhive.foodeyrestaurant.cakes.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.cakes.activities.CakeOrderHistoryDetailActivity;

import java.util.ArrayList;

public class CakeOrderListAdapter extends RecyclerView.Adapter<CakeOrderListAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> customerName;

    public CakeOrderListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cake_history_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        holder.viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CakeOrderHistoryDetailActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView customerName, customerMob, orderId, orderTime;
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
        }
    }
}