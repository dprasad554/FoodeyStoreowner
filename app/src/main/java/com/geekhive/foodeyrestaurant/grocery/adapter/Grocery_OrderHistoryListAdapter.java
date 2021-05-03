package com.geekhive.foodeyrestaurant.grocery.adapter;

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
import com.geekhive.foodeyrestaurant.grocery.activities.Grocery_OrderHistoryDetailActivity;
import com.geekhive.foodeyrestaurant.grocery.beans.grocerystoreorderhistory.OrderDetail;
import com.geekhive.foodeyrestaurant.grocery.beans.grocerystoreorderhistory.OrderHistory;

import java.util.List;

public class Grocery_OrderHistoryListAdapter extends RecyclerView.Adapter<Grocery_OrderHistoryListAdapter.MyViewHolder> {

    Context context;
    List<OrderDetail> orderDetail;

    public Grocery_OrderHistoryListAdapter(Context context, List<OrderDetail> orderDetail ) {
        this.context = context;
        this.orderDetail = orderDetail;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_history_list_item, parent, false);
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final String name = orderDetail.get(position).getUser().getFirstname() + " " + orderDetail.get(position).getUser().getLastname();
        holder.customerName.setText(name);
        holder.customerMob.setText(orderDetail.get(position).getUser().getMobile());
        holder.orderId.setText(orderDetail.get(position).getGCart().getOrderId());
        holder.orderDate.setText(orderDetail.get(position).getGCart().getDate());

        if (orderDetail.get(position).getGCart().getStatus().equals("3")) {
            holder.viewOrder.setText("Confirm");
        } else if (orderDetail.get(position).getGCart().getStatus().equals("2")) {
            holder.viewOrder.setText("Canceled");
        } else if (orderDetail.get(position).getGCart().getStatus().equals("4")) {
            holder.viewOrder.setText("Delivered");
        } else if (orderDetail.get(position).getGCart().getStatus().equals("8")) {
            holder.viewOrder.setText("Packed");
        }

        holder.viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Grocery_OrderHistoryDetailActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("cart_id", orderDetail.get(position).getGCart().getId());
                context.startActivity(intent);
            }
        });

//        holder.productDetailsCard.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, NewOrderDetailsActivity.class);
//                intent.putExtra("position", position);
//                intent.putExtra("cart_id", orderDetail.get(position).getGCart().getId());
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount()
    {
        return orderDetail.size();
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