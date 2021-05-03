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
import com.geekhive.foodeyrestaurant.cakes.beans.cakestoreorderhistory.OrderHistory;

public class CakeOrderHistoryListAdapter extends RecyclerView.Adapter<CakeOrderHistoryListAdapter.MyViewHolder> {

    Context context;
    OrderHistory orderHistory;

    public CakeOrderHistoryListAdapter(Context context, OrderHistory orderHistory ) {
        this.context = context;
        this.orderHistory = orderHistory;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cake_history_list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String name = orderHistory.getOrderDetail().get(position).getUser().getFirstname() + " " + orderHistory.getOrderDetail().get(position).getUser().getLastname();
        holder.customerName.setText(name);
        holder.customerMob.setText(orderHistory.getOrderDetail().get(position).getUser().getMobile());
        holder.orderId.setText(orderHistory.getOrderDetail().get(position).getCakeCart().getOrderId());
        holder.orderDate.setText(orderHistory.getOrderDetail().get(position).getCakeCart().getDate());

        if (orderHistory.getOrderDetail().get(position).getCakeCart().getStatus().equals("3")) {
            holder.viewOrder.setText("Confirm");
        } else if (orderHistory.getOrderDetail().get(position).getCakeCart().getStatus().equals("2")) {
            holder.viewOrder.setText("Canceled");
        } else if (orderHistory.getOrderDetail().get(position).getCakeCart().getStatus().equals("4")) {
            holder.viewOrder.setText("Delivered");
        } else if (orderHistory.getOrderDetail().get(position).getCakeCart().getStatus().equals("8")) {
            holder.viewOrder.setText("Packed");
        }

        holder.viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CakeOrderHistoryDetailActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("cart_id", orderHistory.getOrderDetail().get(position).getCakeCart().getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return orderHistory.getOrderDetail().size();
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
            orderDate = itemView.findViewById(R.id.orderTime);
        }
    }
}