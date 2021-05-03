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

import com.geekhive.foodeyrestaurant.cakes.activities.CakeNewOrderDetailsActivity;
import com.geekhive.foodeyrestaurant.cakes.beans.cakestoreneworder.StoreNewOrder;

public class CakeHomeListAdapter extends RecyclerView.Adapter<CakeHomeListAdapter.MyViewHolder> {

    Context context;
    StoreNewOrder storeNewOrder;

    public CakeHomeListAdapter(Context context, StoreNewOrder storeNewOrder ) {
        this.context = context;
        this.storeNewOrder = storeNewOrder;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cake_product_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String name = storeNewOrder.getOrder().get(position).getUser().getFirstname() + " " + storeNewOrder.getOrder().get(position).getUser().getLastname();
        holder.customerName.setText(name);
        holder.customerMob.setText(storeNewOrder.getOrder().get(position).getUser().getMobile());
        holder.orderId.setText(storeNewOrder.getOrder().get(position).getCakeCart().getOrderId());
        holder.orderDate.setText(storeNewOrder.getOrder().get(position).getCakeCart().getDate());
        holder.viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CakeNewOrderDetailsActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("cart_id",storeNewOrder.getOrder().get(position).getCakeCart().getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return storeNewOrder.getOrder().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView customerName, customerMob, orderId, orderDate;
        Button viewOrder;
        CardView productDetailsCard;
        public MyViewHolder(View itemView) {
            super(itemView);
            viewOrder = itemView.findViewById(R.id.viewOrder);
            customerName = itemView.findViewById(R.id.customerName);
            customerMob = itemView.findViewById(R.id.customerMob);
            orderId = itemView.findViewById(R.id.orderId);
            productDetailsCard = itemView.findViewById(R.id.productDetailsCard);
            orderDate = itemView.findViewById(R.id.orderDate);
        }
    }
}