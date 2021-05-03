package com.geekhive.foodeyrestaurant.restaurant.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.restaurant.activities.NewOrderDetailsActivity;
import com.geekhive.foodeyrestaurant.restaurant.beans.neworder.Order;

public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.MyViewHolder> {
    public Typeface Montserrat_Regular;
    public Typeface Montserrat_SemiBold;
    Context context;
    Order orderListOut;


    public HomeListAdapter(Context context, Order orderListOut) {
        this.context = context;
        this.orderListOut = orderListOut;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        // set the data in items
        final String name = orderListOut.getOrder().get(position).getUser().getFirstname() + " " + orderListOut.getOrder().get(position).getUser().getLastname();
        holder.customerName.setText(name);
        holder.customerMob.setText(orderListOut.getOrder().get(position).getUser().getMobile());
        holder.orderId.setText(orderListOut.getOrder().get(position).getCart().getOrderId());
        holder.orderTime.setText(orderListOut.getOrder().get(position).getCart().getDate());
        holder.viewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewOrderDetailsActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("cart_id", orderListOut.getOrder().get(position).getCart().getId());
                context.startActivity(intent);
            }
        });

        holder.productDetailsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewOrderDetailsActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("cart_id", orderListOut.getOrder().get(position).getCart().getId());
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return orderListOut.getOrder().size();
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