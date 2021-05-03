package com.geekhive.foodeyrestaurant.cakes.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.geekhive.foodeyrestaurant.R;
import com.geekhive.foodeyrestaurant.cakes.beans.cakeorderdetailhistory.OrderDetailHistory;

public class CakeHomeOrderDetailAdapter extends RecyclerView.Adapter<CakeHomeOrderDetailAdapter.MyViewHolder> {

    Context context;
    OrderDetailHistory orderDetailHistory;

    public CakeHomeOrderDetailAdapter(Context context, OrderDetailHistory orderDetailHistory) {
        this.context = context;
        this.orderDetailHistory = orderDetailHistory;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // infalte the item Layout
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cake_order_detail_list, parent, false);
        // set the view's size, margins, paddings and layout parameters
        MyViewHolder vh = new MyViewHolder(v); // pass the view to View Holder
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
      holder.p_name.setText(orderDetailHistory.getOrder().getCakeCartDetail().get(position).getCake().getProductName() +" * ");
        holder.tv_quantity.setText(orderDetailHistory.getOrder().getCakeCartDetail().get(position).getQuantity() + " "
                + orderDetailHistory.getOrder().getCakeCartDetail().get(position).getCake().getQuantityDetail());
    }

    @Override
    public int getItemCount()
    {
        return orderDetailHistory.getOrder().getCakeCartDetail().size();
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