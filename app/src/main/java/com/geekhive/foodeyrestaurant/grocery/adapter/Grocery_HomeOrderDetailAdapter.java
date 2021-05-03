package com.geekhive.foodeyrestaurant.grocery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.grocery.beans.groceryorderdetailhistory.OrderDetailHistory;

public class Grocery_HomeOrderDetailAdapter extends RecyclerView.Adapter<Grocery_HomeOrderDetailAdapter.MyViewHolder> {

    Context context;
    OrderDetailHistory orderDetailHistory;

    public Grocery_HomeOrderDetailAdapter(Context context, OrderDetailHistory orderDetailHistory) {
        this.context = context;
        this.orderDetailHistory = orderDetailHistory;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_order_detail_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
      holder.p_name.setText(orderDetailHistory.getOrder().getGCartDetail().get(position).getGrocery().getProductName() +" * ");
        holder.tv_quantity.setText(orderDetailHistory.getOrder().getGCartDetail().get(position).getQuantity() + " "
                + orderDetailHistory.getOrder().getGCartDetail().get(position).getGrocery().getQuantityDetail());
    }

    @Override
    public int getItemCount()
    {
        return orderDetailHistory.getOrder().getGCartDetail().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        // init the item view's
        TextView p_name, tv_quantity;

        public MyViewHolder(View itemView) {
            super(itemView);
            p_name = itemView.findViewById(R.id.p_name);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
        }
    }
}